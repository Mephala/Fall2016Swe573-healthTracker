package dao;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import persistance.HealthTrackerUser;
import persistance.UserDailyActivity;
import util.CommonUtils;

import java.util.List;

/**
 * Created by Mephalay on 10/4/2016.
 */
public class HealthTrackerUserDao extends BaseDao {
    private static HealthTrackerUserDao instance;

    private HealthTrackerUserDao() {

    }

    public static synchronized HealthTrackerUserDao getInstance() {
        if (instance == null)
            instance = new HealthTrackerUserDao();
        return instance;
    }

    private void initialize(HealthTrackerUser user) {
        Hibernate.initialize(user.getUserWeightChanges());
        Hibernate.initialize(user.getUserTargetNutritions());
        Hibernate.initialize(user.getUserDailyActivities());
        List<UserDailyActivity> dailyActivities = user.getUserDailyActivities();
        if (CommonUtils.notEmpty(dailyActivities)) {
            for (UserDailyActivity dailyActivity : dailyActivities) {
                Hibernate.initialize(dailyActivity.getUserCompletedExercises());
                Hibernate.initialize(dailyActivity.getUserEatenFood());
            }
        }
    }

    public void saveUser(HealthTrackerUser user) {
        Session session = getSessionAndBeginTransaction();
        session.saveOrUpdate(user);
        commitAndTerminateSession(session);
    }

    public List<HealthTrackerUser> fetchUsersByUsername(String username) {
        Session session = getSessionAndBeginTransaction();
        Criteria criteria = session.createCriteria(HealthTrackerUser.class);
        criteria = criteria.add(Restrictions.eq("username", username));
        List<HealthTrackerUser> userList = criteria.list();
        for (HealthTrackerUser healthTrackerUser : userList) {
            initialize(healthTrackerUser);
        }
        commitAndTerminateSession(session);
        return userList;
    }

    public HealthTrackerUser getUserById(String id) {
        Session session = getSessionAndBeginTransaction();
        HealthTrackerUser user = (HealthTrackerUser) session.get(HealthTrackerUser.class, id);
        initialize(user);
        commitAndTerminateSession(session);
        return user;
    }


}
