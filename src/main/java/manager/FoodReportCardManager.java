package manager;

import dao.USFoodReportDao;
import model.FoodQueryResponse;
import model.FoodResponseItem;
import model.FoodResponseList;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import persistance.USFoodInfoCard;
import util.CommonUtils;
import util.HibernateUtil;
import util.WebAPIUtils;

import java.util.List;

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
