package calculation;

import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import util.ActivityLevel;
import util.CalculationUtils;
import util.Gender;

import java.math.BigDecimal;

import static org.junit.Assert.fail;

/**
 * Created by Mephalay on 10/30/2016.
 */
@RunWith(JMockit.class)
public class TestCalculations {

    @Test
    public void testDailyCalorieNeedsOfMale() {
        try {

            BigDecimal dailyCalorieNeeds = CalculationUtils.calculateDailyCalorieNeedMetric(new BigDecimal(83), new BigDecimal(170), 29, ActivityLevel.SEDENTARY, Gender.MALE);
            System.out.println();
            System.out.println(dailyCalorieNeeds.toString());

        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }
}
