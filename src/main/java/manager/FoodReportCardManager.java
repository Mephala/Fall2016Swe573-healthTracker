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

/**
 * Created by Mephalay on 10/29/2016.
 */
public class FoodReportCardManager {

    private static FoodReportCardManager instance;
    private Logger logger = Logger.getLogger(this.getClass());
    private USFoodReportDao usFoodReportDao = USFoodReportDao.getInstance();
    private List<USFoodInfoCard> foodInfoCards = new ArrayList<>();
    private Map<String, List<USFoodInfoCard>> keywordToFoodMap = new HashMap<>();

    private FoodReportCardManager(boolean constructIfNotExist, boolean daoActive) {
        logger.info("Constructing food report card manager...");
        if (daoActive) {
            List<USFoodInfoCard> infoCards = usFoodReportDao.getAllInfoCards();
            ObjectMapper objectMapper = new ObjectMapper();
            if (CommonUtils.isEmpty(infoCards) && constructIfNotExist) {
                logger.info("Food list is empty in the database. Constructing.... THIS MAY TAKE A WHILE");
                List<FoodQueryResponse> foodQueryResponses = WebAPIUtils.queryAllFoods();
                Session session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                for (FoodQueryResponse foodQueryResponse : foodQueryResponses) {
                    FoodResponseList foodResponseList = foodQueryResponse.getList();
                    List<FoodResponseItem> foodResponseItemList = foodResponseList.getItem();
                    for (FoodResponseItem responseItem : foodResponseItemList) {
                        USFoodInfoCard usFoodInfoCard = new USFoodInfoCard();
                        usFoodInfoCard.setFoodName(responseItem.getName());
                        usFoodInfoCard.setNdbno(responseItem.getNdbno());
                        usFoodInfoCard.setDsVal(responseItem.getDs());
                        usFoodInfoCard.setGroupName(responseItem.getGroup());
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
                        }
                        session.saveOrUpdate(usFoodInfoCard);
                        foodInfoCards.add(usFoodInfoCard);
                    }
                }
                session.getTransaction().commit();
                session.close();
            } else {
                this.foodInfoCards = infoCards;
            }
            logger.info("Finished fetching all food info from Database.");
        }
        indexFoods();
        logger.info("Finished constructing FoodReportCardManager.");
    }

    private void indexFoods() {
        long start = System.currentTimeMillis();
        logger.info("Indexing food names...");
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

    public List<USFoodInfoCard> smartSearch(String q) {
        long start = System.currentTimeMillis();
        q = q.toLowerCase();
        List<USFoodInfoCard> retval = keywordToFoodMap.get(q);
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
            logger.info("Food search (via lookup) completed in " + (System.currentTimeMillis() - start) + " ms.");
            return retval;
        } else {
            logger.info("Food search (via cache) completed in " + (System.currentTimeMillis() - start) + " ms.");
            return retval;
        }
    }
}
