package controller;

import manager.ExerciseManager;
import manager.FoodReportCardManager;
import manager.HealthTrackerUserManager;
import model.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import persistance.*;
import util.CalculationUtils;
import util.CommonUtils;
import util.SecurityUtils;
import util.WebAPIUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Mephalay on 10/25/2016.
 */
@Controller
public class FoodController {
    private Logger logger = Logger.getLogger(this.getClass());
    private FoodReportCardManager foodReportCardManager = FoodReportCardManager.getInstance(true, true);
    private ExerciseManager exerciseManager = ExerciseManager.getInstance();
    private HealthTrackerUserManager userManager = HealthTrackerUserManager.getInstance();

    @RequestMapping(value = "/queryFood", method = RequestMethod.GET)
    public Object queryFood(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Showing food querying");
        ModelAndView model = new ModelAndView("foodQuery");
        model.addObject("searchResultFound", Boolean.FALSE); // No search result to show, just initial screen
        return model;
    }

    @RequestMapping(value = "/ajax/queryFoodName", method = RequestMethod.POST, produces = "application/json; charset=utf8", consumes = "application/json; charset=utf8")
    @ResponseBody
    public Object ajaxFoodSearch(HttpServletRequest request, HttpServletResponse response, @RequestBody AjaxSearchRequest ajaxSearchRequest) {
        logger.info("Received ajax search request for food names...");
        List<USFoodInfoCard> foodInfoCards = foodReportCardManager.smartSearch(ajaxSearchRequest.getSearchKeyword());
        List<String> searchResponse = new ArrayList<>();
        Map<String, String> unitMap = new HashMap<>();
        for (USFoodInfoCard foodInfoCard : foodInfoCards) {
            searchResponse.add(foodInfoCard.getFoodName());
            List<PersistedNutrition> persistedNutritionList = foodInfoCard.getPersistedNutritionList();
            if (CommonUtils.notEmpty(persistedNutritionList) && CommonUtils.notEmpty(foodInfoCard.getPersistedNutritionList().get(0).getAvailableAmountUnits())) {
                unitMap.put(foodInfoCard.getFoodName(), foodInfoCard.getPersistedNutritionList().get(0).getAvailableAmountUnits().get(0));
            } else {
                unitMap.put(foodInfoCard.getFoodName(), "g");
            }
        }
        AjaxSearchResponse ajaxSearchResponse = new AjaxSearchResponse();
        ajaxSearchResponse.setAvailableKeywords(searchResponse);
        ajaxSearchResponse.setUnitMap(unitMap);
        return ajaxSearchResponse;
    }

    @RequestMapping(value = "/ajax/retrieveActivities", method = RequestMethod.POST)
    public Object retrieveActivities(HttpServletRequest request, HttpServletResponse response, @RequestBody String date) {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        if (Boolean.FALSE.equals(userSession.getLogin()))
            return null;
        if (!CommonUtils.isValidDate(date))
            return null;
        userSession.setActivityQueryDate(date); // currently showing activities based on this date.
        userSession.getCompletedExercises().clear();
        userSession.getConsumedFoods().clear();
        userSession.setCurrentCalorieIntake(new BigDecimal(0));
        userSession.setCurrentCalorieOutput(new BigDecimal(0));
        userSession.setCalorieOutputPercentage(new BigDecimal(0));
        userSession.setCalorieIntakePercentage(new BigDecimal(0));
        HealthTrackerUser healthTrackerUser = userManager.getUserById(userSession.getUserId());
        List<UserDailyActivity> userDailyActivities = healthTrackerUser.getUserDailyActivities();
        if (CommonUtils.notEmpty(userDailyActivities)) {
            for (UserDailyActivity userDailyActivity : userDailyActivities) {
                if (userDailyActivity.getActivityDate().equals(date)) {
                    List<EatenFood> eatenFoods = userDailyActivity.getUserEatenFood();
                    List<UserCompletedExercise> userCompletedExercises = userDailyActivity.getUserCompletedExercises();
                    if (CommonUtils.isEmpty(eatenFoods) && CommonUtils.isEmpty(userCompletedExercises))
                        return null;
                    List<USFoodInfoCard> consumedFoods = foodReportCardManager.getUSFoodInfoCards(eatenFoods);
                    List<CompletedExercise> completedExercises = exerciseManager.getCompletedExercises(userCompletedExercises, userSession.getWeight());
                    userSession.setCompletedExercises(completedExercises);
                    userSession.setConsumedFoods(consumedFoods);
                    calculateCalorieIntakePercentage(userSession);
                    BigDecimal calorieIntage = new BigDecimal(0);
                    BigDecimal calorieOutput = new BigDecimal(0);
                    if (CommonUtils.notEmpty(eatenFoods)) {
                        for (EatenFood eatenFood : eatenFoods) {
                            calorieIntage = calorieIntage.add(eatenFood.getConsumedCalorie());
                        }
                    }
                    if (CommonUtils.notEmpty(userCompletedExercises)) {
                        for (UserCompletedExercise userCompletedExercise : userCompletedExercises) {
                            calorieOutput = calorieOutput.add(userCompletedExercise.getBurnedCalories());
                        }
                    }
                    userSession.setCurrentCalorieIntake(calorieIntage);
                    userSession.setCurrentCalorieOutput(calorieOutput);
                    ModelAndView modelAndView = new ModelAndView("foodAndActivityUpdate");
                    calculatePercentages(userSession);
                    return modelAndView;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    private void calculatePercentages(UserSession userSession) {
        calculateCalorieIntakePercentage(userSession);
        calculateCalorieOutputPercentage(userSession);
    }

    @RequestMapping(value = "/ajax/addFood")
    public Object loadActivities(HttpServletRequest request, HttpServletResponse response, @RequestBody AjaxAddFoodRequest addFoodRequest) {
        logger.info("Adding food with name:" + addFoodRequest.getAddedFood());
        if (CommonUtils.isEmpty(addFoodRequest.getDate()))
            return null;

        ModelAndView modelAndView = new ModelAndView("foodAndActivityUpdate");
        List<USFoodInfoCard> consumedFood = foodReportCardManager.smartSearch(addFoodRequest.getAddedFood());
        if (CommonUtils.isEmpty(consumedFood)) {
            //TODO handle non-found food.
        }
        if (consumedFood.size() > 1) {
            //TODO handle multiple food candidate
        }
        USFoodInfoCard foodInfoCard = consumedFood.get(0);
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        if (Boolean.FALSE.equals(userSession.getLogin()))
            return null;
        userSession.getConsumedFoods().add(foodInfoCard);
        //FIXME need quantity!!
        List<PersistedNutrition> persistedNutritions = foodInfoCard.getPersistedNutritionList();
        BigDecimal calorieIntakeForRequest = null;
        String availableAmountUnits = "g";
        String amount = addFoodRequest.getAmount();
        ProcessFoodResponse processFoodResponse = new ProcessFoodResponse(userSession, persistedNutritions, calorieIntakeForRequest, availableAmountUnits, amount).invoke();
        calorieIntakeForRequest = processFoodResponse.getCalorieIntakeForRequest();
        availableAmountUnits = processFoodResponse.getAvailableAmountUnits();

        if (calorieIntakeForRequest != null) {
            String userId = userSession.getUserId();
            HealthTrackerUser persistedUser = userManager.getUserById(userId);
            String date = addFoodRequest.getDate();
            String activityId = userId + "_" + date;
            List<UserDailyActivity> userDailyActivities = persistedUser.getUserDailyActivities();

            UserDailyActivity userDailyActivity = new UserDailyActivity();
            userDailyActivity.setDailyActivityId(activityId);
            boolean activityFound = false;
            if (userDailyActivities == null) {
                userDailyActivities = new ArrayList<>();
                persistedUser.setUserDailyActivities(userDailyActivities);
            } else {
                for (UserDailyActivity dailyActivity : userDailyActivities) {
                    if (date.equals(dailyActivity.getActivityDate())) {
                        activityFound = true;
                        userDailyActivity = dailyActivity;
                    }
                }
                userDailyActivity.setActivityDate(date);
            }
            addEatenFoodToUserDailyActivity(addFoodRequest, foodInfoCard, calorieIntakeForRequest, availableAmountUnits, userDailyActivity, persistedUser, activityFound);
            userManager.saveUser(persistedUser);
        }

        return modelAndView;
    }

    private void addEatenFoodToUserDailyActivity(@RequestBody AjaxAddFoodRequest addFoodRequest, USFoodInfoCard foodInfoCard, BigDecimal calorieIntakeForRequest, String availableAmountUnits, UserDailyActivity userDailyActivity, HealthTrackerUser persistedUser, boolean activityFound) {
        List<EatenFood> eatenFoods = userDailyActivity.getUserEatenFood();
        if (eatenFoods == null) {
            eatenFoods = new ArrayList<>();
            userDailyActivity.setUserEatenFood(eatenFoods);
        }
        EatenFood eatenFood = new EatenFood();
        eatenFood.setEatenFoodId(UUID.randomUUID().toString());
        eatenFood.setAmount(addFoodRequest.getAmount());
        eatenFood.setConsumedCalorie(calorieIntakeForRequest);
        eatenFood.setUnit(availableAmountUnits);
        eatenFood.setNbdbno(foodInfoCard.getNdbno());
        eatenFoods.add(eatenFood);
        if (!activityFound)
            persistedUser.getUserDailyActivities().add(userDailyActivity);
    }

    @RequestMapping(value = "/ajax/addExercise")
    public Object addActivities(HttpServletRequest request, HttpServletResponse response, @RequestBody AjaxAddFoodRequest addFoodRequest) {
        logger.info("Adding exercise with name:" + addFoodRequest.getAddedFood());
        ModelAndView modelAndView = new ModelAndView("foodAndActivityUpdate");
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        if (Boolean.FALSE.equals(userSession.getLogin()))
            return null;
        String exerciseName = addFoodRequest.getAddedFood();
        List<Exercise> matchingExercies = exerciseManager.searchExercise(exerciseName);
        if (CommonUtils.isEmpty(matchingExercies)) {
            //TODO handle no match
        }
        if (matchingExercies.size() > 0) {
            //TODO handle multiple match
        }
        Exercise completedExercise = matchingExercies.get(0);
        BigDecimal exerciseDuration = new BigDecimal(addFoodRequest.getAmount());
        BigDecimal calorieExpense = exerciseManager.calculateSpentCalories(completedExercise, userSession.getWeight(), userSession.getWeightUnit(), exerciseDuration);
        CompletedExercise userCompletedExercise = new CompletedExercise();
        userCompletedExercise.setGuid(UUID.randomUUID().toString());
        userCompletedExercise.setExercise(completedExercise);
        userCompletedExercise.setDuration(exerciseDuration);
        userCompletedExercise.setEnergyOutput(calorieExpense);
        userSession.getCompletedExercises().add(userCompletedExercise);
        userSession.setCurrentCalorieOutput(userSession.getCurrentCalorieOutput().add(calorieExpense));
        calculateCalorieOutputPercentage(userSession);

        String date = addFoodRequest.getDate();
        String userId = userSession.getUserId();

        UserCompletedExercise persistedExercise = new UserCompletedExercise();
        persistedExercise.setBurnedCalories(calorieExpense);
        persistedExercise.setCompletedExerciseId(UUID.randomUUID().toString());
        persistedExercise.setExerciseName(exerciseName);
        persistedExercise.setExerciseDuration(exerciseDuration);

        userManager.addExerciseToUserActivities(persistedExercise, date, userId);

        return modelAndView;
    }

    private void calculateCalorieOutputPercentage(UserSession userSession) {
        BigDecimal top = new BigDecimal(100);
        BigDecimal currentCaloriePercentage = CalculationUtils.calculatePercentage(userSession.getCurrentCalorieOutput(), userSession.getSuggestedDailyCalorieSpent());
        if (currentCaloriePercentage.compareTo(top) == 1) {
            userSession.setCalorieOutputPercentage(top);
        } else {
            userSession.setCalorieOutputPercentage(currentCaloriePercentage);
        }
    }

    @RequestMapping(value = "ajax/removeItem", produces = "application/json; charset=utf8", consumes = "application/json; charset=utf8")
    @ResponseBody
    public Object removeItem(HttpServletRequest request, HttpServletResponse response, @RequestBody ItemRemoveRequest itemRemoveRequest) {
        try {
            if (itemRemoveRequest == null) {
                return returnFailItemRemoveResponse("Invalid parameters");
            }
            String itemId = itemRemoveRequest.getItemId();
            String date = itemRemoveRequest.getItemId();
            if (CommonUtils.isEmpty(itemId)) {
                return returnFailItemRemoveResponse("No item selected");
            }
            if (CommonUtils.isEmpty(date)) {
                return returnFailItemRemoveResponse("No date specified");
            }
            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
            if (Boolean.FALSE.equals(userSession.getLogin())) {
                return returnFailItemRemoveResponse("You are not registered/logged in");
            }
            HealthTrackerUser persistentUser = userManager.removeItemFromActivity(itemRemoveRequest, userSession.getUserId(), userSession.getCompletedExercises());
            SecurityUtils.setLoggedInUserSessionParameters(persistentUser, userSession);
            return returnSuccessItemRemoveResponse();
        } catch (Throwable t) {
            logger.error("Failed to remove user request", t);
            return returnFailItemRemoveResponse("Failed to process your request");
        }
    }

    private Object returnSuccessItemRemoveResponse() {
        ItemRemoveResponse removeResponse = new ItemRemoveResponse();
        removeResponse.setCompleted(Boolean.TRUE);
        removeResponse.setPromptMsg("OK");
        return removeResponse;
    }

    private Object returnFailItemRemoveResponse(String prompt) {
        ItemRemoveResponse removeResponse = new ItemRemoveResponse();
        removeResponse.setCompleted(Boolean.FALSE);
        removeResponse.setPromptMsg(prompt);
        return removeResponse;
    }

    @RequestMapping(value = "/ajax/searchExercise")
    @ResponseBody
    public Object searchExercise(HttpServletRequest request, HttpServletResponse response, @RequestBody AjaxSearchRequest searchRequest) {
        AjaxSearchResponse ajaxSearchResponse = new AjaxSearchResponse();
        List<Exercise> exercises = exerciseManager.searchExercise(searchRequest.getSearchKeyword());
        List<String> exerciseNames = new ArrayList<>();
        for (Exercise exercise : exercises) {
            exerciseNames.add(exercise.getType());
        }
        ajaxSearchResponse.setAvailableKeywords(exerciseNames);
        return ajaxSearchResponse;
    }

    @RequestMapping(value = "/doQueryFood", method = RequestMethod.POST)
    public Object queryFood(HttpServletRequest request, HttpServletResponse response, @RequestParam String foodName) {
        logger.info("Querying for  food...");
        try {
            String foodListResponseString = WebAPIUtils.queryFood(foodName);
            ObjectMapper om = new ObjectMapper();
            FoodQueryResponse foodQueryResponse = om.readValue(foodListResponseString, FoodQueryResponse.class);
            FoodResponseList foodResponseList = foodQueryResponse.getList();
            if (foodResponseList == null) {
                ModelAndView model = new ModelAndView("foodQuery");
                return model;
            }
            List<BasicFoodResponse> basicFoodResponseList = new ArrayList<>();
            List<FoodResponseItem> foodResponseItemList = foodResponseList.getItem();
            final int limit = 5;
            int count = 0;
            for (FoodResponseItem item : foodResponseItemList) {
                String dbNo = item.getNdbno();
                String name = item.getName();
                BasicFoodResponse basicFoodResponse = new BasicFoodResponse();
                basicFoodResponse.setTitle(name);
                basicFoodResponse.setJson(WebAPIUtils.queryFoodReport(dbNo));
                basicFoodResponse.setHrefId(UUID.randomUUID().toString());
                count++;
                basicFoodResponseList.add(basicFoodResponse);
                if (count == limit)
                    break;
            }
            BasicFoodResponseList searchResultList = new BasicFoodResponseList();
            searchResultList.setBasicFoodResponseList(basicFoodResponseList);
            ModelAndView model = new ModelAndView("foodQuery");
            if (basicFoodResponseList.size() > 0) {
                model.addObject("searchResultFound", Boolean.TRUE);
                model.addObject("foodResults", searchResultList);
            } else {
                model.addObject("searchResultFound", Boolean.FALSE);

            }
            return model;
        } catch (Throwable t) {
            logger.error("Error occured", t);
            ModelAndView model = new ModelAndView("foodQuery");
            return model;
        }

    }

    private void calculateCalorieIntakePercentage(UserSession userSession) {
        BigDecimal currentCalorieIntakePercentage = CalculationUtils.calculatePercentage(userSession.getCurrentCalorieIntake(), userSession.getDailyCalorieNeed());
        BigDecimal top = new BigDecimal(100);
        if (currentCalorieIntakePercentage.compareTo(top) == 1) {
            userSession.setCalorieIntakePercentage(top);
        } else {
            userSession.setCalorieIntakePercentage(currentCalorieIntakePercentage);
        }
    }

    private class ProcessFoodResponse {
        private UserSession userSession;
        private List<PersistedNutrition> persistedNutritions;
        private BigDecimal calorieIntakeForRequest;
        private String availableAmountUnits;
        private String amount;

        public ProcessFoodResponse(UserSession userSession, List<PersistedNutrition> persistedNutritions, BigDecimal calorieIntakeForRequest, String availableAmountUnits, String amount) {
            this.userSession = userSession;
            this.persistedNutritions = persistedNutritions;
            this.calorieIntakeForRequest = calorieIntakeForRequest;
            this.availableAmountUnits = availableAmountUnits;
            this.amount = amount;
        }

        public BigDecimal getCalorieIntakeForRequest() {
            return calorieIntakeForRequest;
        }

        public String getAvailableAmountUnits() {
            return availableAmountUnits;
        }

        public ProcessFoodResponse invoke() {
            for (PersistedNutrition persistedNutrition : persistedNutritions) {
                if ("Energy".equals(persistedNutrition.getNutritionName())) {
                    BigDecimal currentCalorieIntake = userSession.getCurrentCalorieIntake();
                    if (persistedNutrition.getAvailableAmountUnits() != null && persistedNutrition.getAvailableAmountUnits().size() > 0)
                        availableAmountUnits = persistedNutrition.getAvailableAmountUnits().get(0);
                    calorieIntakeForRequest = CalculationUtils.calculateCalorieIntakeForAmount(persistedNutrition, amount);
                    if (currentCalorieIntake == null) {
                        currentCalorieIntake = calorieIntakeForRequest;
                    } else {
                        currentCalorieIntake = currentCalorieIntake.add(calorieIntakeForRequest);
                    }
                    userSession.setCurrentCalorieIntake(currentCalorieIntake);
                }
            }
            calculateCalorieIntakePercentage(userSession);
            return this;
        }


    }
}
