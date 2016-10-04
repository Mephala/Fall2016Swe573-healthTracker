package manager;

import dao.HealthTrackerUserDao;
import exception.RegistrationException;
import org.apache.log4j.Logger;
import persistance.HealthTrackerUser;
import util.SecurityUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by Mephalay on 10/4/2016.
 */
public class HealthTrackerUserManager {

    private static HealthTrackerUserManager instance;
    private HealthTrackerUserDao dao = HealthTrackerUserDao.getInstance();
    private Logger logger = Logger.getLogger(this.getClass());

    private HealthTrackerUserManager() {
        logger.info("HealthTracker user manager constructed.");
    }

    public static synchronized HealthTrackerUserManager getInstance() {
        if (instance == null)
            instance = new HealthTrackerUserManager();
        return instance;
    }

    public void registerUser(String username, String password) throws RegistrationException {
        try {
            if (username == null || username.isEmpty()) {
                throw new RegistrationException("Username can't be empty", "REG002", "Username can not be empty", null);
            }
            if (password == null || password.isEmpty()) {
                throw new RegistrationException("Password can't be empty", "REG003", "Password can not be empty", null);
            }
            List<HealthTrackerUser> users = dao.fetchUsersByUsername(username);
            if (users != null && users.size() > 0) {
                throw new RegistrationException("Username in use", "REG004", "Username is in use, please pick different username", null);
            }
            HealthTrackerUser userToRegister = new HealthTrackerUser();
            userToRegister.setUserId(UUID.randomUUID().toString());
            userToRegister.setUsername(username);
            userToRegister.setPassword(SecurityUtils.generateHashWithHMACSHA256(password));
            dao.saveUser(userToRegister);
            logger.info("Completed registering user.");
        } catch (Throwable t) {
            logger.error("Failed to register user.", t);
            throw new RegistrationException("Encountered an error during registration", "REG001", "Failed to process your registration,please try again", t);
        }
    }


}
