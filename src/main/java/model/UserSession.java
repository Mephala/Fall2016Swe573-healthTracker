package model;

import persistance.USFoodInfoCard;
import util.ActivityLevel;
import util.Unit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mephalay on 10/2/2016.
 */
public class UserSession {
    private Boolean login = Boolean.FALSE;
    private String username;
    private BigDecimal weight;
    private Unit weightUnit;
    private BigDecimal heigth;
    private Unit heightUnit;
    private Integer age;
    private BigDecimal bmi;
    private String bmiIndicator;
    private ActivityLevel activityLevel;
    private BigDecimal dailyCalorieNeed;
    private List<USFoodInfoCard> consumedFoods = new ArrayList<>();
    private BigDecimal currentCalorieIntake = new BigDecimal(0);
    private BigDecimal currentCalorieOutput = new BigDecimal(0);
    private List<CompletedExercise> completedExercises = new ArrayList<>();
    private BigDecimal calorieIntakePercentage = new BigDecimal(0);
    private BigDecimal suggestedDailyCalorieSpent = new BigDecimal(500);
    private BigDecimal calorieOutputPercentage = new BigDecimal(0);
    private String passwordHash;
    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public String getBmiIndicator() {
        return bmiIndicator;
    }

    public void setBmiIndicator(String bmiIndicator) {
        this.bmiIndicator = bmiIndicator;
    }

    public BigDecimal getCalorieOutputPercentage() {
        return calorieOutputPercentage;
    }

    public void setCalorieOutputPercentage(BigDecimal calorieOutputPercentage) {
        this.calorieOutputPercentage = calorieOutputPercentage;
    }

    public BigDecimal getSuggestedDailyCalorieSpent() {
        return suggestedDailyCalorieSpent;
    }

    public void setSuggestedDailyCalorieSpent(BigDecimal suggestedDailyCalorieSpent) {
        this.suggestedDailyCalorieSpent = suggestedDailyCalorieSpent;
    }

    public BigDecimal getCalorieIntakePercentage() {
        return calorieIntakePercentage;
    }

    public void setCalorieIntakePercentage(BigDecimal calorieIntakePercentage) {
        this.calorieIntakePercentage = calorieIntakePercentage;
    }

    public BigDecimal getCurrentCalorieOutput() {
        return currentCalorieOutput;
    }

    public void setCurrentCalorieOutput(BigDecimal currentCalorieOutput) {
        this.currentCalorieOutput = currentCalorieOutput;
    }

    public List<CompletedExercise> getCompletedExercises() {
        return completedExercises;
    }

    public void setCompletedExercises(List<CompletedExercise> completedExercises) {
        this.completedExercises = completedExercises;
    }

    public BigDecimal getCurrentCalorieIntake() {
        return currentCalorieIntake;
    }

    public void setCurrentCalorieIntake(BigDecimal currentCalorieIntake) {
        this.currentCalorieIntake = currentCalorieIntake;
    }

    public List<USFoodInfoCard> getConsumedFoods() {
        return consumedFoods;
    }

    public void setConsumedFoods(List<USFoodInfoCard> consumedFoods) {
        this.consumedFoods = consumedFoods;
    }

    public BigDecimal getDailyCalorieNeed() {
        return dailyCalorieNeed;
    }

    public void setDailyCalorieNeed(BigDecimal dailyCalorieNeed) {
        this.dailyCalorieNeed = dailyCalorieNeed;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Unit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Unit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public BigDecimal getHeigth() {
        return heigth;
    }

    public void setHeigth(BigDecimal heigth) {
        this.heigth = heigth;
    }

    public Unit getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(Unit heightUnit) {
        this.heightUnit = heightUnit;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }
}
