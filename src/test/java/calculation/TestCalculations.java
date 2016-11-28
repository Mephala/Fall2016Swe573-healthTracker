package calculation;

import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import util.ActivityLevel;
import util.CalculationUtils;
import util.Gender;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Test
    public void testKgToLBSConverstion(){
        try{
            BigDecimal kg = new BigDecimal("85");
            BigDecimal lbs = CalculationUtils.kgToLbs(kg);
            System.out.println();
            System.out.println(lbs);
            ArrayList<String> k = new ArrayList<>();
            System.out.println(k instanceof List);
            System.out.println(List.class.isAssignableFrom(k.getClass()));
            System.out.println(List.class.isAssignableFrom(ArrayList.class));
            System.out.println(ArrayList.class.isAssignableFrom(List.class));
            System.out.println(k.getClass().isAssignableFrom(List.class));
            System.out.println(ArrayList.class.isAssignableFrom(k.getClass()));
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }

    private Object generateRandomClass() {
        long t = System.currentTimeMillis();
        if (t % 2 == 0) {
            return List.class;
        }
        if (t % 3 == 00) {
            return new LinkedList<>();

        }
        return new HashMap<>();
    }

    @Test
    public void typeCheck() {
        try {
            Object returnType = generateRandomClass();
            System.out.println();
            System.out.println(new BigDecimal("-3"));
            String k = "gokhan";


            if (returnType instanceof List) {
                System.out.println("List");
            }
            if (returnType instanceof ArrayList) {
                System.out.println("ArrayList");
            }

        }catch(Throwable t){
            t.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSDF() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println();
            System.out.println(sdf.format(new Date()));

        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }
}
