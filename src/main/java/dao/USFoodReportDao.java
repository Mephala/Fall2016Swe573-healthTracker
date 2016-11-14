package dao;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import persistance.PersistedNutrition;
import persistance.USFoodInfoCard;
import util.CommonUtils;

import java.util.List;

/**
 * Created by Mephalay on 10/29/2016.
 */
public class USFoodReportDao extends BaseDao {

    private static USFoodReportDao instance;

    private void initialize(USFoodInfoCard infoCard) {
        Hibernate.initialize(infoCard.getPersistedNutritionList());
        List<PersistedNutrition> persistedNutritions = infoCard.getPersistedNutritionList();
        if (CommonUtils.notEmpty(persistedNutritions)) {
            for (PersistedNutrition persistedNutrition : persistedNutritions) {
                Hibernate.initialize(persistedNutrition.getAvailableAmountUnits());
            }
        }
    }


    private USFoodReportDao() {

    }

    public static synchronized USFoodReportDao getInstance() {
        if (instance == null)
            instance = new USFoodReportDao();
        return instance;
    }


    public void saveOrUpdate(USFoodInfoCard infoCard) {
        Session session = getSessionAndBeginTransaction();
        session.saveOrUpdate(infoCard);
        commitAndTerminateSession(session);
    }

    public List<USFoodInfoCard> getAllInfoCards() {
        Session session = getSessionAndBeginTransaction();
        Criteria criteria = session.createCriteria(USFoodInfoCard.class);
        List<USFoodInfoCard> retval = criteria.list();
        for (USFoodInfoCard usFoodInfoCard : retval) {
            initialize(usFoodInfoCard);
        }
        commitAndTerminateSession(session);
        return retval;
    }


}
