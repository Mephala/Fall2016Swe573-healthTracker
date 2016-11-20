package dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import persistance.HealthTrackerUser;

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
        commitAndTerminateSession(session);
        return userList;
    }

    public HealthTrackerUser getUserById(String id) {
        Session session = getSessionAndBeginTransaction();
        HealthTrackerUser user = (HealthTrackerUser) session.get(HealthTrackerUser.class, id);
        commitAndTerminateSession(session);
        return user;
    }


}
