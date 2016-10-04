package dao;

import org.hibernate.Session;
import util.HibernateUtil;

/**
 * Created by Mephalay on 10/4/2016.
 */
public abstract class BaseDao {

    protected Session getSessionAndBeginTransaction() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        return session;
    }

    protected void commitAndTerminateSession(Session session) {
        if (session != null) {
            session.getTransaction().commit();
            session.close();
        }
    }
}
