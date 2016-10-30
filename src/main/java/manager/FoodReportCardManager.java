package manager;

import dao.USFoodReportDao;
import model.*;
import org.apache.http.HttpException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Session;
import persistance.PersistedNutrition;
import persistance.USFoodInfoCard;
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

    private static FoodReportCardManager instance;
    private Logger logger = Logger.getLogger(this.getClass());
    private USFoodReportDao usFoodReportDao = USFoodReportDao.getInstance();
    private List<USFoodInfoCard> foodInfoCards = new ArrayList<>();
    private Map<String, List<USFoodInfoCard>> keywordToFoodMap = new HashMap<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private static final long FOOD_SYNC_SCHEDULE_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days
    private Lock readLock;
    private Lock writeLock;
    private volatile boolean completedFoodSync = false;
    private static final int MAX_SEARCH_RESPONSE = 5;
    private static final int MIN_SEARCH_KEYWORD_LEN = 2;
    private Map<String, List<USFoodInfoCard>> apiSearchCache = new HashMap<>();

    private FoodReportCardManager(final boolean constructIfNotExist, final boolean daoActive) {
        logger.info("Constructing food report card manager...");
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        readLock = reentrantReadWriteLock.readLock();
        writeLock = reentrantReadWriteLock.writeLock();
        completedFoodSync = true;
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                asyncFoodScheduleJob(constructIfNotExist, daoActive);
            }
        }, 0L, FOOD_SYNC_SCHEDULE_TIME, TimeUnit.MILLISECONDS);
        logger.info("Finished constructing FoodReportCardManager.");
    }

    private void asyncFoodScheduleJob(final boolean constructIfNotExist, final boolean daoActive) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    syncFoodDatabase(constructIfNotExist, daoActive);
                } catch (Throwable t) {
                    logger.fatal("Failed to process async construction of foodReportCard manager.");
                }
            }
        }).start();
    }

    private void syncFoodDatabase(boolean constructIfNotExist, boolean daoActive) throws URISyntaxException, IOException, HttpException {
        logger.info("Processing async food db sync job...");
        if (daoActive) {
            List<USFoodInfoCard> infoCards = usFoodReportDao.getAllInfoCards();
            this.foodInfoCards = infoCards;
            int foodNumberInUSDb = WebAPIUtils.getFoodNum();
            boolean missingDBData = (CommonUtils.isEmpty(infoCards) || infoCards.size() < foodNumberInUSDb);
            ObjectMapper objectMapper = new ObjectMapper();
            if (missingDBData && constructIfNotExist) {
                logger.info("Food list is not synced within USDB. Syncing.... THIS MAY TAKE A WHILE");
                List<FoodQueryResponse> foodQueryResponses = WebAPIUtils.queryAllFoods();
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
                        writeLock.unlock();
                        indexFoods();
                    }
                }
            }
            logger.info("Finished fetching all food info from Database.");
            indexFoods();
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
                if (apiNutrition.getMeasures().size() > 0) {
                    measuredAmount = new BigDecimal(apiNutrition.getMeasures().get(0).getQty());
                    measuredValue = new BigDecimal(apiNutrition.getMeasures().get(0).getValue());
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

    public static synchronized FoodReportCardManager getInstance(boolean constructIfNotExist, boolean daoActive) {
        if (instance == null)
            instance = new FoodReportCardManager(constructIfNotExist, daoActive);
        return instance;
    }

    public void feedFoodInfoCards(List<USFoodInfoCard> foodInfoCards) {
        this.foodInfoCards.addAll(foodInfoCards);
        indexFoods();
    }

    public List<USFoodInfoCard> smartSearch(String q, final int searchLimit) {
        readLock.lock();
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

    public List<USFoodInfoCard> smartSearch(String q) {
        return smartSearch(q, MIN_SEARCH_KEYWORD_LEN);
    }
}
