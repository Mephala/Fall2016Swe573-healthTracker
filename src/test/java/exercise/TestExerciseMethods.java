package exercise;

import manager.ExerciseManager;
import mockit.integration.junit4.JMockit;
import model.Exercise;
import org.junit.Test;
import org.junit.runner.RunWith;
import util.CommonUtils;
import util.Unit;

import java.math.BigDecimal;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mephalay on 11/14/2016.
 */
@RunWith(JMockit.class)
public class TestExerciseMethods {

    @Test
    public void constructExerciseManager() {
        try {
            ExerciseManager exerciseManager = ExerciseManager.getInstance();
            assertTrue(exerciseManager != null);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSearchingForExercise() {
        try {
            String q = "aerobic";
            ExerciseManager exerciseManager = ExerciseManager.getInstance();
            List<Exercise> exerciseList = exerciseManager.searchExercise(q);
            assertTrue(CommonUtils.notEmpty(exerciseList));
            System.out.println();
            System.out.println(exerciseList);
            q = "WRESTLING";
            exerciseList = exerciseManager.searchExercise(q);
            assertTrue(CommonUtils.notEmpty(exerciseList));
            System.out.println(exerciseList);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }

    @Test
    public void testCalculatingExerciseCaloriesForWeight(){
        try{
            ExerciseManager exerciseManager = ExerciseManager.getInstance();
            BigDecimal weight = new BigDecimal(88);
            Unit weightUnit = Unit.METRIC;
            String activityName = "Wrestling";
            BigDecimal duration = new BigDecimal("1.5"); //hours
            Exercise exercise = exerciseManager.searchExercise(activityName).get(0);
            BigDecimal spentCalories = exerciseManager.calculateSpentCalories(exercise,weight,weightUnit,duration);
            System.out.println();
            System.out.println(spentCalories);
            duration = new BigDecimal("1");
            weight = new BigDecimal(130);
            weightUnit = Unit.IMPERIAL;
            spentCalories = exerciseManager.calculateSpentCalories(exercise,weight,weightUnit,duration);
            System.out.println(spentCalories);
            duration = new BigDecimal("1");
            weight = new BigDecimal(155);
            weightUnit = Unit.IMPERIAL;
            spentCalories = exerciseManager.calculateSpentCalories(exercise,weight,weightUnit,duration);
            System.out.println(spentCalories);
        }catch(Throwable t){
            t.printStackTrace();
            fail();
        }
    }
}
