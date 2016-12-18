package property.dao;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;
import property.model.SPSProperty;
import util.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class SPSPropDao {

    private static SPSPropDao instance;
    private Log logger = LogFactory.getLog(getClass());

    private SPSPropDao() {

    }

    public static synchronized SPSPropDao getInstance() {
        if (instance == null)
            instance = new SPSPropDao();
        return instance;
    }

    public void saveProp(SPSProperty prop) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(prop);
            session.getTransaction().commit();
            session.close();
        } catch (Throwable t) {
            logger.error("Error", t);
        }
    }

    @SuppressWarnings("unchecked")
    public List<SPSProperty> getAllProps() {
        try {

            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Criteria crit = session.createCriteria(SPSProperty.class);
            List<SPSProperty> retval = crit.list();
            session.getTransaction().commit();
            session.close();
            return retval;
        } catch (Throwable t) {
            logger.error("Error", t);
        }
        return Collections.emptyList();
    }

    /**
     * Returns prop if it is found, null otherwise.
     */
    public SPSProperty findPropByKey(String key) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Criteria crit = session.createCriteria(SPSProperty.class).add(Restrictions.eq("propKey", key));
            @SuppressWarnings("unchecked")
            List<SPSProperty> props = crit.list();
            if (CollectionUtils.isEmpty(props))
                return null;
            SPSProperty retval = props.get(0);
            session.getTransaction().commit();
            session.close();
            return retval;
        } catch (Throwable t) {
            logger.error("Error", t);
        }
        return null;
    }
}
