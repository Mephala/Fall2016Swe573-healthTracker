package persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by Mephalay on 11/21/2016.
 */
@Entity(name = "USER_TARGET_NUTRITION")
public class TargetNutrition {

    @Id
    @Column(name = "TARGET_NUTRITION_ID")
    private String targetId;

    @Column(name = "TARGET_NUTRITION_NAME", nullable = false)
    private String nutritionName;

    @Column(name = "TARGET_NUTRITION_AMOUNT", nullable = false)
    private BigDecimal nutritionAmount;

    @Column(name = "TARGET_NUTRITION_UNIT", nullable = false)
    private String nutritionUnit;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    public TargetNutrition() {

    }

    public TargetNutrition(String userId, String key, String value, String unit) {
        this.userId = userId;
        this.nutritionName = key;
        this.nutritionAmount = new BigDecimal(value);
        this.nutritionUnit = unit;
        this.targetId = UUID.randomUUID().toString();
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getNutritionName() {
        return nutritionName;
    }

    public void setNutritionName(String nutritionName) {
        this.nutritionName = nutritionName;
    }

    public BigDecimal getNutritionAmount() {
        return nutritionAmount;
    }

    public void setNutritionAmount(BigDecimal nutritionAmount) {
        this.nutritionAmount = nutritionAmount;
    }

    public String getNutritionUnit() {
        return nutritionUnit;
    }

    public void setNutritionUnit(String nutritionUnit) {
        this.nutritionUnit = nutritionUnit;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
