package util;

import model.UserSession;

import java.math.BigDecimal;

/**
 * Created by Mephalay on 10/30/2016.
 */
public class CalculationUtils {

    public static BigDecimal calculateDailyCalorieNeedMetric(BigDecimal weight, BigDecimal height, Integer age, ActivityLevel activityLevel, Gender gender) {
        BigDecimal base = new BigDecimal(gender.getBaseCoefficient());
        BigDecimal weightAdd = gender.getWeightCoefficient().multiply(weight);
        BigDecimal heightAdd = gender.getHeightCoefficient().multiply(height);
        BigDecimal ageAdd = gender.getAgeCoefficient().multiply(new BigDecimal(age));
        BigDecimal bmr = base.add(weightAdd).add(heightAdd).subtract(ageAdd);
        return activityLevel.getCoefficient().multiply(bmr).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calculatePercentage(BigDecimal value, BigDecimal total) {
        if (total == null || value == null)
            return new BigDecimal(0);
        return value.multiply(new BigDecimal(100)).divide(total, 2, BigDecimal.ROUND_HALF_UP);
    }
}
