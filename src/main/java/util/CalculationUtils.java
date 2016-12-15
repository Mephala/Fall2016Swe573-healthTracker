package util;

import exception.RegistrationException;
import persistance.EatenFood;
import persistance.PersistedNutrition;
import persistance.UserCompletedExercise;
import persistance.UserDailyActivity;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

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

    public static BigDecimal calculateCalorieIntakeForAmount(PersistedNutrition persistedNutrition, String amount, String unit) {
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

    public static BigDecimal calculateCalorieIntakeForDailyActivity(UserDailyActivity userActivity) {
        if (userActivity == null)
            return new BigDecimal(0);
        List<EatenFood> eatenFoods = userActivity.getUserEatenFood();
        if (CommonUtils.isEmpty(eatenFoods))
            return new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);
        for (EatenFood eatenFood : eatenFoods) {
            total = total.add(eatenFood.getConsumedCalorie());
        }
        return total;
    }

    public static BigDecimal calculateCalorieOutputForDailyActivity(UserDailyActivity userActivity) {
        if (userActivity == null)
            return new BigDecimal(0);
        List<UserCompletedExercise> completedExercises = userActivity.getUserCompletedExercises();
        if (CommonUtils.isEmpty(completedExercises))
            return new BigDecimal(0);
        BigDecimal total = new BigDecimal(0);
        for (UserCompletedExercise completedExercise : completedExercises) {
            total = total.add(completedExercise.getBurnedCalories());
        }
        return total;
    }

    public static String calculateAge(String age) throws RegistrationException {
        if (CommonUtils.isEmpty(age))
            throw new RegistrationException("Please enter your date of birth", "HT0023", "age is empty", null);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int dashIndex = age.indexOf("-");
        if (dashIndex == -1)
            throw new RegistrationException("Please enter valid date of birth", "HT0024", "age is invalid", null);
        int userYear = Integer.parseInt(age.substring(0, dashIndex));
        return Integer.valueOf(year - userYear).toString();
    }
}
