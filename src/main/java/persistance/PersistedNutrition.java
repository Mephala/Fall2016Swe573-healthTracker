package persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by Mephalay on 10/29/2016.
 */
@Entity(name = "NUTRITION")
public class PersistedNutrition {

    @Id
    @Column(name = "NUTRITION_ID")
    private String nutritionGuid;


    @Column(name = "NUTRITION_NDB_ID")
    private Integer nutritionId;

    @Column(name = "NUTRITION_NAME")
    private String nutritionName;

    @Column(name = "NUTRITION_UNIT")
    private String nutritionUnit;

    @Column(name = "NUTRITION_MEASURED_AMOUNT")
    private BigDecimal nutritionMeasuredAmount;

    @Column(name = "NUTRITION_MEASURED_VALUE")
    private BigDecimal nutritionMeasuredValue;

    @Column(name = "NUTRITION_UNIT_VALUE")
    private BigDecimal nutritionUnitValue;

    public String getNutritionGuid() {
        return nutritionGuid;
    }

    public void setNutritionGuid(String nutritionGuid) {
        this.nutritionGuid = nutritionGuid;
    }

    public Integer getNutritionId() {
        return nutritionId;
    }

    public void setNutritionId(Integer nutritionId) {
        this.nutritionId = nutritionId;
    }

    public String getNutritionName() {
        return nutritionName;
    }

    public void setNutritionName(String nutritionName) {
        this.nutritionName = nutritionName;
    }

    public String getNutritionUnit() {
        return nutritionUnit;
    }

    public void setNutritionUnit(String nutritionUnit) {
        this.nutritionUnit = nutritionUnit;
    }

    public BigDecimal getNutritionMeasuredAmount() {
        return nutritionMeasuredAmount;
    }

    public void setNutritionMeasuredAmount(BigDecimal nutritionMeasuredAmount) {
        this.nutritionMeasuredAmount = nutritionMeasuredAmount;
    }

    public BigDecimal getNutritionMeasuredValue() {
        return nutritionMeasuredValue;
    }

    public void setNutritionMeasuredValue(BigDecimal nutritionMeasuredValue) {
        this.nutritionMeasuredValue = nutritionMeasuredValue;
    }

    public BigDecimal getNutritionUnitValue() {
        return nutritionUnitValue;
    }

    public void setNutritionUnitValue(BigDecimal nutritionUnitValue) {
        this.nutritionUnitValue = nutritionUnitValue;
    }
}
