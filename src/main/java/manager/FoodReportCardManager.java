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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mephalay on 10/29/2016.
 */
public class FoodReportCardManager {

    private static FoodReportCardManager instance;
    private Logger logger = Logger.getLogger(this.getClass());
    private USFoodReportDao usFoodReportDao = USFoodReportDao.getInstance();

    private FoodReportCardManager() {
        logger.info("Constructing food report card manager...");
        List<USFoodInfoCard> infoCards = usFoodReportDao.getAllInfoCards();
        ObjectMapper objectMapper = new ObjectMapper();
        if (CommonUtils.isEmpty(infoCards)) {
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
                }
            }
            session.getTransaction().commit();
            session.close();
        }
        logger.info("Finished constructing FoodReportCardManager...");
    }

    public static synchronized FoodReportCardManager getInstance() {
        if (instance == null)
            instance = new FoodReportCardManager();
        return instance;
    }

}
