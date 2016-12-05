package manager;

import dao.HealthTrackerUserDao;
import exception.LoginException;
import exception.RegistrationException;
import model.*;
import org.apache.log4j.Logger;
import persistance.*;
import util.*;

import java.math.BigDecimal;
import java.util.*;

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

    public void saveUser(HealthTrackerUser user) {
        dao.saveUser(user);
    }

    public HealthTrackerUser getUserById(String id) {
        return dao.getUserById(id);
    }

    public HealthTrackerUser registerUser(RegisterForm registerForm) throws RegistrationException {
        try {
            String username = registerForm.getUsername();
            String password = registerForm.getPassword();
            String gender = registerForm.getFemale();
            String height = registerForm.getHeight();
            String weight = registerForm.getWeight();
            Unit weightUnit = Unit.forName(registerForm.getWeightSelect());
            Unit heightUnit = Unit.forName(registerForm.getHeightSelect());
            String age = registerForm.getAge();
            if (!CommonUtils.isNumeric(age)) {
                throw new RegistrationException("Please enter a numeric age", "REG009", "Please enter a numeric age", null);
            }
            if (heightUnit != weightUnit) {
                throw new RegistrationException("Please enter your height and weight units ALL Metric, or ALL Imperial. Don't confuse us!", "REG008", "Please enter your height and weight units ALL Metric, or ALL Imperial. Don't confuse us!", null);
            }
            ActivityLevel activityLevel = ActivityLevel.forName(registerForm.getExerciseLevel());
            Gender userGender = Gender.forName(gender);
            if (CommonUtils.isEmpty(gender)) {
                gender = registerForm.getMale();
                userGender = Gender.forName(gender);
                if (CommonUtils.isEmpty(gender)) {
                    throw new RegistrationException("Gender can not be empty", "REG005", "Username can not be empty", null);
                }
            }
            if (!CommonUtils.isNumeric(height)) {
                throw new RegistrationException("Height must be numeric", "REG006", "Username can not be empty", null);
            }
            if (!CommonUtils.isNumeric(weight)) {
                throw new RegistrationException("Weight must be numeric", "REG007", "Username can not be empty", null);
            }
            if (username == null || username.isEmpty()) {
                throw new RegistrationException("Username can't be empty", "REG002", "Username can not be empty", null);
            }
            if (password == null || password.isEmpty()) {
                throw new RegistrationException("Password can't be empty", "REG003", "Password can not be empty", null);
            }
            BigDecimal weightVal = new BigDecimal(weight);
            BigDecimal heightVal = new BigDecimal(height);
            List<HealthTrackerUser> users = dao.fetchUsersByUsername(username);
            if (users != null && users.size() > 0) {
                throw new RegistrationException("Username in use", "REG004", "Username is in use, please pick different username", null);
            }
            HealthTrackerUser userToRegister = new HealthTrackerUser();
            Integer userAge = Integer.parseInt(age);
            userToRegister.setAge(userAge);
            userToRegister.setUserId(UUID.randomUUID().toString());
            userToRegister.setUsername(username);
            userToRegister.setPassword(SecurityUtils.generateHashWithHMACSHA256(password));
            userToRegister.setGender(userGender);
            userToRegister.setActivityLevel(activityLevel);
            userToRegister.setHeight(heightVal);
            userToRegister.setWeight(weightVal);
            userToRegister.setHeightUnit(heightUnit);
            userToRegister.setWeightUnit(weightUnit);
            dao.saveUser(userToRegister);
            logger.info("Completed registering user.");
            return userToRegister;
        } catch (Throwable t) {
            if (t instanceof RegistrationException) {
                throw (RegistrationException) t;
            }
            logger.error("Failed to register user.", t);
            throw new RegistrationException("Encountered an error during registration", "REG001", "Failed to process your registration,please try again", t);
        }
    }

    public HealthTrackerUser loginUser(String username, String password) throws LoginException {
        try {
            if (username == null || username.isEmpty()) {
                throw new LoginException("Username can't be empty", "LOG002", "Username can not be empty", null);
            }
            if (password == null || password.isEmpty()) {
                throw new LoginException("Password can't be empty", "LOG003", "Password can not be empty", null);
            }
            List<HealthTrackerUser> users = dao.fetchUsersByUsername(username);
            if (users == null || users.size() == 0) {
                throw new LoginException("No user with such user", "LOG004", "Invalid credentials", null);
            }
            for (HealthTrackerUser user : users) {
                String hashedPw = SecurityUtils.generateHashWithHMACSHA256(password);
                if (user.getPassword().equals(hashedPw)) {
                    return user;
                }
            }
            throw new LoginException("No user matches that password", "LOG005", "Invalid credentials", null);
        } catch (Throwable t) {
            logger.error("Failed to login user with username:" + username, t);
            throw new LoginException("Error during login process", "LOG001", "Failed to process your login request, please try again later", t);
        }
    }

    public HealthTrackerUser updateUser(String userId, UserProfileModel userProfileModel) {
        HealthTrackerUser persistedUser = dao.getUserById(userId);
        String newPw = userProfileModel.getPassword();
        if (CommonUtils.notEmpty(newPw)) {
            persistedUser.setPassword(SecurityUtils.generateHashWithHMACSHA256(newPw)); // OldPw check must be done in controller.
        }
        BigDecimal previousWeight = persistedUser.getWeight();
        BigDecimal newWeight = userProfileModel.getWeight();
        if (!previousWeight.equals(newWeight)) {
            BigDecimal change = newWeight.subtract(previousWeight);
            List<UserWeightChange> previousChanges = persistedUser.getUserWeightChanges();
            if (previousChanges == null) {
                previousChanges = new ArrayList<>();
                persistedUser.setUserWeightChanges(previousChanges);
            }
            UserWeightChange userWeightChange = new UserWeightChange();
            userWeightChange.setChangeId(UUID.randomUUID().toString());
            userWeightChange.setChangeTime(new Date());
            userWeightChange.setCurrentWeight(newWeight);
            userWeightChange.setPreviousWeight(previousWeight);
            userWeightChange.setWeightChange(change);
            userWeightChange.setUserId(userId);
            previousChanges.add(userWeightChange);
        }
        persistedUser.setWeight(userProfileModel.getWeight());
        persistedUser.setHeight(userProfileModel.getHeight());
        persistedUser.setUsername(userProfileModel.getUsername());
        dao.saveUser(persistedUser);
        return persistedUser;
    }


    public HealthTrackerUser createUserTargets(String userId, Map<String, String> targetsMap) {
        HealthTrackerUser persistedUser = dao.getUserById(userId);
        String targetWeight = targetsMap.get("targetWeight");
        BigDecimal targetWeightVal = new BigDecimal(targetWeight);
        persistedUser.setTargetWeight(targetWeightVal);
        List<TargetNutritionModel> nutritionModels = FoodReportCardManager.getInstance(true, true).getTargetNutritionModels();
        if (targetsMap.size() > 1) {
            Set<String> keyset = targetsMap.keySet();
            for (String key : keyset) {
                if ("targetWeight".equals(key)) {
                    //omitting, we already added targetWeight
                    continue;
                }
                //If user manually posts invalid nutrition names, he can overwhelm database so this check is necessary.

                TargetNutritionModel targetNutritionModel = null;
                for (TargetNutritionModel nutritionModel : nutritionModels) {
                    if (key.equals(nutritionModel.getName())) {
                        targetNutritionModel = nutritionModel;
                        break;
                    }
                }
                if (targetNutritionModel != null) {
                    TargetNutrition targetNutrition = new TargetNutrition(userId, key, targetsMap.get(key), targetNutritionModel.getUnit());
                    List<TargetNutrition> userTargetNutritions = persistedUser.getUserTargetNutritions();
                    if (userTargetNutritions == null) {
                        userTargetNutritions = new ArrayList<>();
                        persistedUser.setUserTargetNutritions(userTargetNutritions);
                    }
                    userTargetNutritions.add(targetNutrition);
                }
            }
        }
        dao.saveUser(persistedUser);
        return persistedUser;
    }

    public void addExerciseToUserActivities(UserCompletedExercise persistedExercise, String date, String userId) {
        HealthTrackerUser user = getUserById(userId);
        List<UserDailyActivity> userActivities = user.getUserDailyActivities();
        if (userActivities == null) {
            userActivities = new ArrayList<>();
            user.setUserDailyActivities(userActivities);
        }
        UserDailyActivity userDailyActivity = null;
        for (UserDailyActivity userActivity : userActivities) {
            if (date.equals(userActivity.getActivityDate())) {
                userDailyActivity = userActivity;
            }
        }
        if (userDailyActivity == null) {
            userDailyActivity = new UserDailyActivity();
            userDailyActivity.setActivityDate(date);
            userDailyActivity.setDailyActivityId(UUID.randomUUID().toString() + "_" + date);
            userActivities.add(userDailyActivity);
        }
        List<UserCompletedExercise> userExercises = userDailyActivity.getUserCompletedExercises();
        if (userExercises == null) {
            userExercises = new ArrayList<>();
            userDailyActivity.setUserCompletedExercises(userExercises);
        }
        userExercises.add(persistedExercise);
        logger.info("Adding exercise to user activities for user:" + userId);
        saveUser(user);
    }

    public HealthTrackerUser removeItemFromActivity(ItemRemoveRequest itemRemoveRequest, String userId, List<CompletedExercise> completedExercises) {
        HealthTrackerUser persistentUser = getUserById(userId);
        List<UserDailyActivity> userActivities = persistentUser.getUserDailyActivities();
        String activityDate = itemRemoveRequest.getDate();
        UserDailyActivity userDailyActivity = findUserActivityByDate(userActivities, activityDate);
        boolean dirty = false;
        if (userDailyActivity != null) {
            EatenFood eatenFood = findEatenFoodById(itemRemoveRequest, userDailyActivity);
            if (eatenFood != null) {
                removeFoodFromActivity(eatenFood, userDailyActivity);
                dirty = true;
            } else {
                UserCompletedExercise userCompletedExercise = findUserCompletedExerciseByHash(itemRemoveRequest, completedExercises, userDailyActivity);
                if (userCompletedExercise != null) {
                    removeExerciseFromActivity(userCompletedExercise, userDailyActivity);
                    dirty = true;
                }
            }
        }
        if (dirty)
            saveUser(persistentUser);
        return persistentUser;
    }

    private void removeExerciseFromActivity(UserCompletedExercise userCompletedExercise, UserDailyActivity userDailyActivity) {
        userDailyActivity.getUserCompletedExercises().remove(userCompletedExercise);
    }

    private UserCompletedExercise findUserCompletedExerciseByHash(ItemRemoveRequest itemRemoveRequest, List<CompletedExercise> completedExercises, UserDailyActivity userDailyActivity) {
        UserCompletedExercise userCompletedExercise = null;
        List<UserCompletedExercise> userCompletedExercises = userDailyActivity.getUserCompletedExercises();
        for (CompletedExercise cexercise : completedExercises) {
            if (cexercise.getHash() == Integer.valueOf(Integer.parseInt(itemRemoveRequest.getItemId()))) {
                Exercise exercise = cexercise.getExercise();
                for (UserCompletedExercise completedExercise : userCompletedExercises) {
                    if (completedExercise.getExerciseName().equals(exercise.getType())) {
                        userCompletedExercise = completedExercise;
                        break;
                    }
                }
            }
        }
        return userCompletedExercise;
    }

    private void removeFoodFromActivity(EatenFood eatenFood, UserDailyActivity userDailyActivity) {
        userDailyActivity.getUserEatenFood().remove(eatenFood);
    }

    private EatenFood findEatenFoodById(ItemRemoveRequest itemRemoveRequest, UserDailyActivity userDailyActivity) {
        EatenFood eatenFood = null;
        List<EatenFood> eatenFoods = userDailyActivity.getUserEatenFood();
        for (EatenFood food : eatenFoods) {
            if (food.getNbdbno().equals(itemRemoveRequest.getItemId())) {
                eatenFood = food;
                break;
            }
        }
        return eatenFood;
    }

    private UserDailyActivity findUserActivityByDate(List<UserDailyActivity> userActivities, String activityDate) {
        UserDailyActivity userDailyActivity = null;
        for (UserDailyActivity userActivity : userActivities) {
            if (userActivity.getActivityDate().equals(activityDate)) {
                userDailyActivity = userActivity;
                break;
            }
        }
        return userDailyActivity;
    }
}
