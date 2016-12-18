package manager;

import dao.USFoodReportDao;
import model.*;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import persistance.EatenFood;
import persistance.PersistedNutrition;
import persistance.USFoodInfoCard;
import property.manager.SPSPropertyManager;
import util.CommonUtils;
import util.HibernateUtil;
import util.WebAPIUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Mephalay on 10/29/2016.
 */
public class FoodReportCardManager {

    private static final long FOOD_SYNC_SCHEDULE_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days
    private static final int MAX_SEARCH_RESPONSE = 50;
    private static final boolean DISABLE_API_SEARCH = true;
    private static final int MIN_SEARCH_KEYWORD_LEN = 2;
    private static FoodReportCardManager instance;
    private Logger logger = Logger.getLogger(this.getClass());
    private USFoodReportDao usFoodReportDao = USFoodReportDao.getInstance();
    private List<USFoodInfoCard> foodInfoCards = new ArrayList<>();
    private Map<String, List<USFoodInfoCard>> keywordToFoodMap = new HashMap<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private Lock readLock;
    private Lock writeLock;
    private volatile boolean completedFoodSync = false;
    private Map<String, List<USFoodInfoCard>> apiSearchCache = new HashMap<>();
    private Set<TargetNutritionModel> targetNutritionModels = new HashSet<>();

    private FoodReportCardManager(final boolean constructIfNotExist, final boolean daoActive) {
        logger.info("Constructing food report card manager...");
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                asyncFoodScheduleJob(constructIfNotExist, daoActive);
            }
        }, 0L, FOOD_SYNC_SCHEDULE_TIME, TimeUnit.MILLISECONDS);
        logger.info("Finished constructing FoodReportCardManager.");
    }

    public static synchronized FoodReportCardManager getInstance(boolean constructIfNotExist, boolean daoActive) {
        if (instance == null)
            instance = new FoodReportCardManager(constructIfNotExist, daoActive);
        return instance;
    }

    private void asyncFoodScheduleJob(final boolean constructIfNotExist, final boolean daoActive) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    syncFoodDatabase(constructIfNotExist, daoActive);
                } catch (Throwable t) {
                    logger.fatal("Failed to process async construction of foodReportCard manager.", t);
                }
            }
        }).start();
    }

    private void syncFoodDatabase(boolean constructIfNotExist, boolean daoActive) throws URISyntaxException, IOException, HttpException {
        logger.info("Processing async food db sync job...");
        if (daoActive) {
            List<USFoodInfoCard> infoCards = usFoodReportDao.getAllInfoCards();
            if (CommonUtils.isEmpty(infoCards) && "remote".equals(SPSPropertyManager.getInstance().getStringProperty("server.ENV"))) {
                retrieveFromServer();
                try {
                    System.gc(); // retrieval from server is pretty memory intensive. This is a good point to GC, although nothing depends on it.
                } catch (Throwable t) {
                    logger.error("Failed to GC");
                }
                logger.info("Checking persisted data...");
                infoCards = usFoodReportDao.getAllInfoCards();
                logger.info("Done checking persisted data...");
            }
            this.foodInfoCards = infoCards;
            logger.info("Creating nutrition data table...");
            for (USFoodInfoCard infoCard : infoCards) {
                setTargetNutritionModels(infoCard);
            }
            logger.info("Done creating nutrition data table...");
            indexFoods();
            logger.info("Fetching total number of foods in USDB API...");
            int foodNumberInUSDb = WebAPIUtils.getFoodNum();
            logger.info("Fetched total number of foods in USDB API :" + foodNumberInUSDb);
            boolean missingDBData = (CommonUtils.isEmpty(infoCards) || infoCards.size() < foodNumberInUSDb);
            ObjectMapper objectMapper = new ObjectMapper();
            if (missingDBData && constructIfNotExist) {
                logger.info("Food list is not synced within USDB. Syncing.... THIS MAY TAKE A WHILE");
                List<FoodQueryResponse> foodQueryResponses = WebAPIUtils.queryAllFoods();
                final int foodIndexLimit = 150;
                int newlyAddedFood = 0;
                for (FoodQueryResponse foodQueryResponse : foodQueryResponses) {
                    logger.info("Filling individual food report slots for each offSet...");
                    FoodResponseList foodResponseList = foodQueryResponse.getList();
                    List<FoodResponseItem> foodResponseItemList = foodResponseList.getItem();
                    for (FoodResponseItem responseItem : foodResponseItemList) {
                        USFoodInfoCard usFoodInfoCard = new USFoodInfoCard();
                        usFoodInfoCard.setFoodName(responseItem.getName());
                        usFoodInfoCard.setNdbno(responseItem.getNdbno());
                        if (infoCards.contains(usFoodInfoCard)) {
                            logger.info("Found item in the database, skipping.");
                            continue;
                        }
                        newlyAddedFood++;
                        Session session = HibernateUtil.getSessionFactory().openSession();
                        session.beginTransaction();
                        usFoodInfoCard.setDsVal(responseItem.getDs());
                        usFoodInfoCard.setGroupName(responseItem.getGroup());
                        attachNutritionInformationToFood(objectMapper, usFoodInfoCard);
                        session.saveOrUpdate(usFoodInfoCard);
                        session.getTransaction().commit();
                        session.close();
                        writeLock.lock();
                        foodInfoCards.add(usFoodInfoCard);
                        setTargetNutritionModels(usFoodInfoCard);
                        writeLock.unlock();
                    }
                    if (newlyAddedFood >= foodIndexLimit) {
                        indexFoods();
                        newlyAddedFood = 0;
                    }
                }
            }
            logger.info("Finished all Sync jobs and creating nutrition tables. Final indexing is going to take place...");
            indexFoods();
            logger.info("All data-structure creation is completed. Now everything can be searched : )");
            writeLock.lock();
            completedFoodSync = true;
            writeLock.unlock();
        }
    }

    private void attachNutritionInformationToFood(ObjectMapper objectMapper, USFoodInfoCard usFoodInfoCard) {
        try {
            String foodReport = WebAPIUtils.queryFoodReport(usFoodInfoCard.getNdbno());
            FoodNutritionResponse foodNutritionResponse = objectMapper.readValue(foodReport, FoodNutritionResponse.class);
            List<PersistedNutrition> foodPersistedNutritions = new ArrayList<>();
            Report nutritionReport = foodNutritionResponse.getReport();
            Food food = nutritionReport.getFood();
            List<Nutrient> apiNutritions = food.getNutrients();
            for (Nutrient apiNutrition : apiNutritions) {
                PersistedNutrition persistedNutrition = new PersistedNutrition();
                persistedNutrition.setNutritionGuid(UUID.randomUUID().toString());
                persistedNutrition.setNutritionId(Integer.parseInt(apiNutrition.getNutrientId()));
                BigDecimal measuredAmount = null;
                BigDecimal measuredValue = null;
                if (apiNutrition != null && apiNutrition.getMeasures().size() > 0) {
                    measuredAmount = new BigDecimal(apiNutrition.getMeasures().get(0).getQty());
                    measuredValue = new BigDecimal(apiNutrition.getMeasures().get(0).getValue());
                    List<String> availableUnits = new ArrayList<>();
                    for (int i = 0; i < apiNutrition.getMeasures().size(); i++) {
                        availableUnits.add(apiNutrition.getMeasures().get(i).getLabel());
                    }
                    persistedNutrition.setAvailableAmountUnits(availableUnits);
                }
                BigDecimal unitValue = new BigDecimal(apiNutrition.getValue());
                persistedNutrition.setNutritionUnit(apiNutrition.getUnit());
                persistedNutrition.setNutritionName(apiNutrition.getName());
                persistedNutrition.setNutritionMeasuredAmount(measuredAmount);
                persistedNutrition.setNutritionUnitValue(unitValue);
                persistedNutrition.setNutritionMeasuredValue(measuredValue);
                foodPersistedNutritions.add(persistedNutrition);
            }
            usFoodInfoCard.setPersistedNutritionList(foodPersistedNutritions);
        } catch (URISyntaxException e) {
            logger.error("Error during food nutrition allocation", e);
        } catch (IOException e) {
            logger.error("Error during food nutrition allocation", e);
        } catch (HttpException e) {
            logger.error("Error during food nutrition allocation", e);
        } catch (Throwable t) {
            logger.error("Error during food nutrition allocation", t);
        }
    }

    private void indexFoods() {
        long start = System.currentTimeMillis();
        logger.info("Indexing food names...");
        writeLock.lock();
        keywordToFoodMap.clear();
        for (USFoodInfoCard foodInfoCard : foodInfoCards) {
            String plainName = foodInfoCard.getFoodName();
            mapWordToFood(foodInfoCard, plainName);
            plainName = plainName.toLowerCase();
            mapWordToFood(foodInfoCard, plainName);
            plainName = plainName.replaceAll(",", "");
            String[] words = plainName.split(" ");
            for (String word : words) {
                mapWordToFood(foodInfoCard, word);
                if (word.length() >= 5) {
                    String init2 = word.substring(0, 2);
                    String init3 = word.substring(0, 3);
                    String init4 = word.substring(0, 4);
                    String init5 = word.substring(0, 5);
                    mapWordToFood(foodInfoCard, init2);
                    mapWordToFood(foodInfoCard, init3);
                    mapWordToFood(foodInfoCard, init4);
                    mapWordToFood(foodInfoCard, init5);
                } else if (word.length() == 4) {
                    String init2 = word.substring(0, 2);
                    String init3 = word.substring(0, 3);
                    String init4 = word.substring(0, 4);
                    mapWordToFood(foodInfoCard, init2);
                    mapWordToFood(foodInfoCard, init3);
                    mapWordToFood(foodInfoCard, init4);
                } else if (word.length() == 3) {
                    String init2 = word.substring(0, 2);
                    String init3 = word.substring(0, 3);
                    mapWordToFood(foodInfoCard, init2);
                    mapWordToFood(foodInfoCard, init3);
                }
            }
        }
        writeLock.unlock();
        logger.info("Finished indexing food names in " + (System.currentTimeMillis() - start) + " ms.");
    }

    private void mapWordToFood(USFoodInfoCard foodInfoCard, String word) {
        if (keywordToFoodMap.containsKey(word)) {
            if (!keywordToFoodMap.get(word).contains(foodInfoCard))
                keywordToFoodMap.get(word).add(foodInfoCard);
        } else {
            List<USFoodInfoCard> searchList = new ArrayList<>();
            searchList.add(foodInfoCard);
            keywordToFoodMap.put(word, searchList);
        }
    }

    public void feedFoodInfoCards(List<USFoodInfoCard> foodInfoCards) {
        this.foodInfoCards.addAll(foodInfoCards);
        indexFoods();
    }

    public List<USFoodInfoCard> smartSearch(String q, final int searchLimit) {
        readLock.lock();
        logger.info("Searching for query:" + q);
        try {
            long start = System.currentTimeMillis();
            if (CommonUtils.isEmpty(q)) {
                logger.info("Food search (Empty query) completed in " + (System.currentTimeMillis() - start) + " ms.");
                return Collections.emptyList();
            }
            q = q.toLowerCase();
            if (!(q.length() >= MIN_SEARCH_KEYWORD_LEN)) {
                logger.info("Food search (min limit not passed, query:" + q + ") completed in " + (System.currentTimeMillis() - start) + " ms.");
                return Collections.emptyList();
            }
            List<USFoodInfoCard> retval = keywordToFoodMap.get(q);
            if (CommonUtils.notEmpty(retval) && retval.size() > searchLimit) {
                retval = retval.subList(0, searchLimit);
            }
            if (CommonUtils.isEmpty(retval)) {
                retval = new ArrayList<>();
                Collection<List<USFoodInfoCard>> values = keywordToFoodMap.values();
                Set<String> idSet = new HashSet<>();
                for (List<USFoodInfoCard> usFoodInfoCardList : values) {
                    for (USFoodInfoCard usFoodInfoCard : usFoodInfoCardList) {
                        String name = usFoodInfoCard.getFoodName();
                        name = name.toLowerCase();
                        if (name.contains(q) && !idSet.contains(usFoodInfoCard.getNdbno())) {
                            retval.add(usFoodInfoCard);
                            idSet.add(usFoodInfoCard.getNdbno());
                        }
                    }
                }
                if (CommonUtils.notEmpty(retval)) {
                    logger.info("Food search (via lookup) completed in " + (System.currentTimeMillis() - start) + " ms.");
                    return retval;
                } else {
                    if (!completedFoodSync) {
                        if (apiSearchCache.containsKey(q)) {
                            logger.info("Food search (search cache) completed in " + (System.currentTimeMillis() - start) + " ms.");
                            return apiSearchCache.get(q);
                        }
                        if (!DISABLE_API_SEARCH) {
                            logger.info("Food search return no response with cached/stored data. Calling API...");
                            String foodsWithName = WebAPIUtils.queryFood(q);
                            ObjectMapper om = new ObjectMapper();
                            FoodQueryResponse foodQueryResponse = om.readValue(foodsWithName, FoodQueryResponse.class);
                            if (foodQueryResponse.getList() != null) {
                                List<FoodResponseItem> foodResponseItem = foodQueryResponse.getList().getItem();
                                if (CommonUtils.notEmpty(foodResponseItem) && foodResponseItem.size() > searchLimit) {
                                    foodResponseItem = foodResponseItem.subList(0, searchLimit);
                                }
                                if (CommonUtils.notEmpty(foodResponseItem)) {
                                    List<USFoodInfoCard> infoCards = new ArrayList<>();
                                    for (FoodResponseItem responseItem : foodResponseItem) {
                                        USFoodInfoCard usFoodInfoCard = new USFoodInfoCard();
                                        usFoodInfoCard.setNdbno(responseItem.getNdbno());
                                        usFoodInfoCard.setFoodName(responseItem.getName());
                                        usFoodInfoCard.setGroupName(responseItem.getGroup());
                                        attachNutritionInformationToFood(om, usFoodInfoCard);
                                        infoCards.add(usFoodInfoCard);
                                    }
                                    apiSearchCache.put(q, infoCards);
                                    logger.info("Food search (via API) completed in " + (System.currentTimeMillis() - start) + " ms.");
                                    return infoCards;
                                } else {
                                    logger.info("Food search (via API) completed in " + (System.currentTimeMillis() - start) + " ms.");
                                    return Collections.emptyList();
                                }
                            } else {
                                logger.info("Food search (via API) completed in " + (System.currentTimeMillis() - start) + " ms.");
                                return Collections.emptyList();
                            }
                        } else {
                            logger.info("API Search disabled, search result is empty");
                            return Collections.emptyList();
                        }
                    } else {
                        logger.info("No related food found, API sync was complete so no further API call was needed. Response in " + (System.currentTimeMillis() - start) + " ms.");
                        return Collections.emptyList();
                    }
                }
            } else {
                logger.info("Food search (via cache) completed in " + (System.currentTimeMillis() - start) + " ms.");
                return retval;
            }
        } catch (Throwable t) {
            logger.error("Failed to complete a smart search", t);
            return Collections.emptyList();
        } finally {
            readLock.unlock();
        }
    }

    public List<TargetNutritionModel> getTargetNutritionModels() {
        readLock.lock();
        try {
            List<TargetNutritionModel> retval = new ArrayList<>();
            for (TargetNutritionModel targetNutritionModel : targetNutritionModels) {
                retval.add(targetNutritionModel);
            }
            return retval;
        } catch (Throwable t) {
            logger.error("Failed to return target nutritions");
            return Collections.emptyList();
        } finally {
            readLock.unlock();
        }
    }

    private void setTargetNutritionModels(USFoodInfoCard usFoodInfoCard) {
        try {
            if (usFoodInfoCard != null) {
                List<PersistedNutrition> persistedNutritions = usFoodInfoCard.getPersistedNutritionList();
                if (CommonUtils.notEmpty(persistedNutritions)) {
                    for (PersistedNutrition persistedNutrition : persistedNutritions) {
                        String nutritionName = persistedNutrition.getNutritionName();
                        String nutritionUnit = persistedNutrition.getNutritionUnit();
                        TargetNutritionModel targetNutritionModel = new TargetNutritionModel();
                        targetNutritionModel.setName(nutritionName);
                        targetNutritionModel.setUnit(nutritionUnit);
                        targetNutritionModels.add(targetNutritionModel);
                    }
                }
            }

        } catch (Throwable t) {
            logger.error("Failed to set target nutrition models!", t);
        }

    }

    public List<USFoodInfoCard> smartSearch(String q) {
        if (CommonUtils.isEmpty(q))
            return Collections.emptyList();
        String[] qs = q.split(" ");
        List<Set<USFoodInfoCard>> searchResults = new ArrayList<>();
        for (String s : qs) {
            if (s.length() >= MIN_SEARCH_KEYWORD_LEN) {
                searchResults.add(CommonUtils.convertListToSet(smartSearch(s, MAX_SEARCH_RESPONSE)));
            }
        }
        List<USFoodInfoCard> retval = new ArrayList<>();
        if (searchResults.size() > 1) {
            Set<USFoodInfoCard> results = searchResults.get(0); //Doesnt matter which set we pick up.
            for (USFoodInfoCard result : results) {
                for (int i = 1; i < searchResults.size(); i++) {
                    if (searchResults.get(i).contains(result)) {
                        retval.add(result);
                    } else {
                        retval.remove(result);
                    }
                }
            }
            return retval;
        } else {
            return CommonUtils.convertSetToList(searchResults.get(0));
        }
    }

    public List<USFoodInfoCard> getUSFoodInfoCards(List<EatenFood> eatenFoods) {
        logger.info("Fetching consumed foods by eaten foods...");
        long start = System.currentTimeMillis();
        List<USFoodInfoCard> retval = new ArrayList<>();
        readLock.lock();
        try {
            if (CommonUtils.notEmpty(eatenFoods)) {
                for (EatenFood eatenFood : eatenFoods) {
                    for (USFoodInfoCard foodInfoCard : foodInfoCards) {
                        if (foodInfoCard.getNdbno().equals(eatenFood.getNbdbno()))
                            retval.add(foodInfoCard);
                    }
                }
            }
        } catch (Throwable t) {
            logger.error("Failed to fetch foodInfoCards by eaten foods...");
        } finally {
            readLock.unlock();
        }
        logger.info("Completed creating foodInfoCard list by eaten foods in " + (System.currentTimeMillis() - start) + " ms.");
        return retval;
    }

    public List<USFoodInfoCard> getAllFoodInfoCards() {
        readLock.lock();
        try {
            return foodInfoCards;
        } catch (Throwable t) {
            logger.error("Failed to fetch foodInfoCards by eaten foods...");
            return Collections.emptyList();
        } finally {
            readLock.unlock();
        }
    }

    private void retrieveFromServer() {
        try {
            String serverResponseString = WebAPIUtils.getProcessedFoods();
            ObjectMapper om = new ObjectMapper();
            ProcessedFoods processedFoods = om.readValue(serverResponseString, ProcessedFoods.class);
            List<USFoodInfoCard> processedInfoCards = processedFoods.getFoods();
            if (CommonUtils.isEmpty(processedInfoCards))
                return;
            BigDecimal total = new BigDecimal(processedInfoCards.size());
            BigDecimal HUNDRED = new BigDecimal(100);
            int added = 0;
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (USFoodInfoCard processedInfoCard : processedInfoCards) {
                session.save(processedInfoCard);
                added++;
                if (added % 150 == 0) {
                    session.flush();
                    session.clear();
                    BigDecimal current = new BigDecimal(added);
                    BigDecimal completionRatio = current.multiply(HUNDRED).divide(total, 3, BigDecimal.ROUND_HALF_UP);
                    logger.info("Completed processing foods:" + completionRatio.toPlainString() + "%");
                }
            }
            session.getTransaction().commit();
            session.close();
            logger.info("Done remote processing...");
        } catch (Throwable t) {
            logger.fatal("Failed to retrieve food items from server", t);
        }
    }
}
