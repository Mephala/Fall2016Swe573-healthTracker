package property.manager;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import property.SPSDefaultProperties;
import property.dao.SPSPropDao;
import property.model.SPSProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SPSPropertyManager {

    private static SPSPropertyManager instance;
    private final Lock readLock;
    private final Lock writeLock;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Long SCHEDULE_PROP_RATE = 1000L * 60L * 5L;
    private Log logger = LogFactory.getLog(getClass());
    private SPSPropDao dao = SPSPropDao.getInstance();
    private Map<String, String> propMap = new HashMap<>();

    private SPSPropertyManager() {
        ReadWriteLock rwLock = new ReentrantReadWriteLock(false);
        this.readLock = rwLock.readLock();
        this.writeLock = rwLock.writeLock();
        initializeDefaultKeys();
        prepareMapAndStartExecutor();
    }

    public static synchronized SPSPropertyManager getInstance() {
        if (instance == null)
            instance = new SPSPropertyManager();
        return instance;
    }

    private void prepareMapAndStartExecutor() {
        preparePropMap();
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    preparePropMap();
                } catch (Throwable t) {
                    logger.fatal("Failed to schedule properties...", t);
                }

            }
        }, SCHEDULE_PROP_RATE, SCHEDULE_PROP_RATE, TimeUnit.MILLISECONDS);
    }

    private void preparePropMap() {
        try {
            writeLock.lock();
            propMap.clear();
            List<SPSProperty> savedProperties = dao.getAllProps();
            for (SPSProperty spsProperty : savedProperties) {
                propMap.put(spsProperty.getPropKey(), spsProperty.getPropValue());
            }
            logger.info("Properties have been initialized.");
        } catch (Throwable t) {
            logger.fatal("Prop remap failure", t);
        } finally {
            writeLock.unlock();
        }
    }

    private void initializeDefaultKeys() {
        Map<String, String> defProps = SPSDefaultProperties.getDefaultProps();
        Set<String> keys = defProps.keySet();
        for (String key : keys) {
            SPSProperty property = dao.findPropByKey(key);
            if (property == null) {
                property = new SPSProperty();
                property.setPropKey(key);
                property.setPropValue(defProps.get(key));
                dao.saveProp(property);
            }
        }
    }

    public String getStringProperty(String key) {
        return getStringProperty(key, null);
    }

    public String getStringProperty(String key, String def) {
        try {
            readLock.lock();
            String value = propMap.get(key);
            if (value == null)
                return def;
            return value;
        } catch (Throwable t) {
            logger.fatal("Read prop failure", t);
        } finally {
            readLock.unlock();
        }
        return def;
    }

    public Long getLongProperty(String key, Long def) {
        try {
            readLock.lock();
            String value = propMap.get(key);
            if (value == null)
                return def;
            return Long.parseLong(value);
        } catch (Throwable t) {
            logger.fatal("Read prop failure", t);
        } finally {
            readLock.unlock();
        }
        return def;
    }

    public Long getLongProperty(String key) {
        return getLongProperty(key, null);
    }

    public Boolean getBooleanProperty(String key, Boolean def) {
        try {
            readLock.lock();
            String value = propMap.get(key);
            if (value == null)
                return def;
            return Boolean.parseBoolean(value);
        } catch (Throwable t) {
            logger.fatal("Read prop failure", t);
        } finally {
            readLock.unlock();
        }
        return def;
    }

    public Boolean getBooleanProperty(String key) {
        return getBooleanProperty(key, null);
    }

    public Integer getIntegerProperty(String key, Integer def) {
        try {
            readLock.lock();
            String value = propMap.get(key);
            if (value == null)
                return def;
            return Integer.parseInt(value);
        } catch (Throwable t) {
            logger.fatal("Read prop failure", t);
        } finally {
            readLock.unlock();
        }
        return def;
    }

    public Integer getIntegerProperty(String key) {
        return getIntegerProperty(key, null);
    }

    public Map<String, String> getPropertyMap() {
        Map<String, String> defCopy = new HashMap<>();
        try {
            readLock.lock();
            Set<String> keys = propMap.keySet();
            for (String key : keys) {
                defCopy.put(key, propMap.get(key));
            }
        } catch (Throwable t) {
            logger.fatal("Read prop failure", t);
        } finally {
            readLock.unlock();
        }
        return defCopy;
    }
}
