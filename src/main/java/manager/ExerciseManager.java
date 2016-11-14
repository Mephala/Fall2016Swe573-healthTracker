package manager;

import model.Exercise;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import util.CalculationUtils;
import util.CommonUtils;
import util.Unit;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mephalay on 11/14/2016.
 */
public class ExerciseManager {
    private static ExerciseManager instance;
    private Logger logger = Logger.getLogger(this.getClass());
    private final List<Exercise> exerciseList = new ArrayList<>();

    private ExerciseManager() {
        logger.info("Constructing Exercise Manager...");
        try {
            URL resloc = this.getClass().getClassLoader().getResource("activities.html");
            File resourceFile = new File(resloc.getPath());
            String resourceAsString = FileUtils.readFileToString(resourceFile);
            String tag = "</tr>";
            int index = resourceAsString.indexOf(tag);
            resourceAsString = resourceAsString.substring(index + tag.length());
            tag = "<tr>";
            index = resourceAsString.indexOf(tag);
            while (index != -1) {
                resourceAsString = resourceAsString.substring(index + tag.length());
                tag = "<td>";
                index = resourceAsString.indexOf(tag);
                resourceAsString = resourceAsString.substring(index + tag.length());
                tag = "</td>";
                index = resourceAsString.indexOf(tag);
                String activityName = resourceAsString.substring(0, index);
                Exercise exercise = new Exercise();
                exercise.setType(activityName);
                resourceAsString = resourceAsString.substring(index + tag.length());
                tag = "<div align=\"right\">";
                index = resourceAsString.indexOf(tag);
                resourceAsString = resourceAsString.substring(index + tag.length());
                tag = "</div>";
                index = resourceAsString.indexOf(tag);
                String calorieString = resourceAsString.substring(0, index);
                Integer calorie = Integer.parseInt(calorieString);
                exercise.setCalorie130lbs(calorie);
                tag = "<tr>";
                index = resourceAsString.indexOf(tag);
                exerciseList.add(exercise);
            }
        } catch (Throwable t) {
            throw new RuntimeException("!!!FAILED TO CONSTRUCT ExerciseManager!!!");
        }
    }

    public static synchronized ExerciseManager getInstance() {
        if (instance == null)
            instance = new ExerciseManager();
        return instance;
    }

    public List<Exercise> searchExercise(String q) {
        List<Exercise> retval = new ArrayList<>();
        if(CommonUtils.isEmpty(q))
            return retval;
        q = q.toLowerCase();
        for (Exercise exercise : exerciseList) {
            if (exercise.getType().toLowerCase().contains(q)) {
                retval.add(exercise);
            }
        }
        return retval;
    }

    public BigDecimal calculateSpentCalories(Exercise exercise, BigDecimal weight, Unit weightUnit, BigDecimal duration) {
        if(Unit.METRIC == weightUnit)
            weight = CalculationUtils.kgToLbs(weight);
        return CalculationUtils.calculateCalorieOutput(exercise.getCalorie130lbs(),weight,duration);
    }
}
