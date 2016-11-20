package persistance;

import util.ActivityLevel;
import util.Gender;
import util.Unit;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Mephalay on 10/2/2016.
 */
@Entity(name = "HEALTH_TRACKER_USER")
public class HealthTrackerUser {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "USER_HEIGHT")
    private BigDecimal height;

    @Column(name = "USER_WEIGHT")
    private BigDecimal weight;

    @Column(name = "HEIGHT_UNIT")
    @Enumerated(EnumType.STRING)
    private Unit heightUnit;

    @Column(name = "WEIGHT_UNIT")
    @Enumerated(EnumType.STRING)
    private Unit weightUnit;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "ACTIVITY_LEVEL")
    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    @Column(name = "AGE")
    private Integer age;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserWeightChange> userWeightChanges;

    public List<UserWeightChange> getUserWeightChanges() {
        return userWeightChanges;
    }

    public void setUserWeightChanges(List<UserWeightChange> userWeightChanges) {
        this.userWeightChanges = userWeightChanges;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Unit getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(Unit heightUnit) {
        this.heightUnit = heightUnit;
    }

    public Unit getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(Unit weightUnit) {
        this.weightUnit = weightUnit;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
