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
    private ActivityLevel activityLevel;
    private BigDecimal dailyCalorieNeed;
    private List<USFoodInfoCard> consumedFoods = new ArrayList<>();
    private BigDecimal currentCalorieIntake;


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
