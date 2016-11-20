package model;

import util.ActivityLevel;
import util.Unit;

import java.math.BigDecimal;

/**
 * Created by Mephalay on 11/20/2016.
 */
public class UserProfileModel {

    private String username;
    private BigDecimal weight;
    private BigDecimal height;
    private Unit weightUnit;
    private Unit heightUnit;
    private Integer age;
    private ActivityLevel activityLevel;
    private String password;
    private String oldPassword;

    public UserProfileModel() {

    }

    public UserProfileModel(UserSession userSession) {
        this.username = userSession.getUsername();
        this.weight = userSession.getWeight();
        this.height = userSession.getHeigth();
        this.weightUnit = userSession.getWeightUnit();
        this.heightUnit = userSession.getHeightUnit();
        this.age = userSession.getAge();
        this.activityLevel = userSession.getActivityLevel();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Unit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Unit weightUnit) {
        this.weightUnit = weightUnit;
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
}
