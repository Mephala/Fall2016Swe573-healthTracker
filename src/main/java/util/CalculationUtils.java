package util;

import persistance.PersistedNutrition;

import java.math.BigDecimal;

/**
 * Created by Mephalay on 10/30/2016.
 */
public class CalculationUtils {

    private static final BigDecimal ONE_KG_TO_POUNDS = new BigDecimal("2.20462");
    private static final BigDecimal UNIT_WEIGHT_IMPERIAL_130LBS = new BigDecimal(130);

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

    public static BigDecimal calculateCalorieIntakeForAmount(PersistedNutrition persistedNutrition, String amount) {
        if (persistedNutrition == null || CommonUtils.isEmpty(amount))
            return new BigDecimal(0);
        if (!CommonUtils.isNumeric(amount))
            return new BigDecimal(0);
        BigDecimal amountBd = new BigDecimal(amount);
        BigDecimal measuredAmount = persistedNutrition.getNutritionMeasuredAmount();
        BigDecimal measuredValue = persistedNutrition.getNutritionMeasuredValue();
        BigDecimal result = amountBd.multiply(measuredValue).divide(measuredAmount, 0, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    public static BigDecimal kgToLbs(BigDecimal lbs) {
        return lbs.multiply(ONE_KG_TO_POUNDS);
    }

    public static BigDecimal calculateCalorieOutput(Integer calorie130lbs, BigDecimal weight, BigDecimal duration) {
        BigDecimal calorieSpentUnitFor130lbs = new BigDecimal(calorie130lbs);
        return weight.multiply(calorieSpentUnitFor130lbs).divide(UNIT_WEIGHT_IMPERIAL_130LBS, 10, BigDecimal.ROUND_HALF_UP).multiply(duration);
    }

    public static BigDecimal calculateBMI(BigDecimal weight, BigDecimal height, Unit heightUnit) {
        if (Unit.IMPERIAL.equals(heightUnit)) {
            weight = weight.multiply(new BigDecimal("0.453592"));
            height = height.multiply(new BigDecimal("2.54"));
        }
        height = height.divide(new BigDecimal(100), 10, BigDecimal.ROUND_HALF_UP); // height in meters.
        return weight.divide(height.multiply(height), 1, BigDecimal.ROUND_HALF_UP);
    }

    public static String findBmiIndicator(BigDecimal bmi) {
        String indicator;
        BigDecimal suw = new BigDecimal("16.5");
        BigDecimal uw = new BigDecimal("18.5");
        BigDecimal n = new BigDecimal("25");
        BigDecimal ow = new BigDecimal("30");
        if (bmi.compareTo(suw) == -1) {
            indicator = "You are severely underweight";
        } else if (bmi.compareTo(suw) > -1 && bmi.compareTo(uw) == -1) {
            indicator = "You are underweight";
        } else if (bmi.compareTo(uw) > -1 && bmi.compareTo(n) == -1) {
            indicator = "You are normal";
        } else if (bmi.compareTo(n) > -1 && bmi.compareTo(ow) == -1) {
            indicator = "You are overweight";
        } else {
            indicator = "You are Obese : (";
        }
        return indicator;
    }
}
