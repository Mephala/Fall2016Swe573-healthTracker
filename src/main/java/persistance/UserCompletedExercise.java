package persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by Mephalay on 11/27/2016.
 */
@Entity(name = "USER_COMPLETED_EXERCISE")
public class UserCompletedExercise {

    @Id
    @Column(name = "COMPLETED_EXERCISE_ID")
    private String completedExerciseId;

    @Column(name = "EXERCISE_NAME")
    private String exerciseName;

    @Column(name = "EXERCISE_DURATION")
    private BigDecimal exerciseDuration;

    @Column(name = "BURNED_CALORIES")
    private BigDecimal burnedCalories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCompletedExercise that = (UserCompletedExercise) o;

        return completedExerciseId.equals(that.completedExerciseId);

    }

    @Override
    public String toString() {
        return "UserCompletedExercise{" +
                "exerciseName='" + exerciseName + '\'' +
                ", exerciseDuration=" + exerciseDuration +
                '}';
    }

    @Override
    public int hashCode() {
        return completedExerciseId.hashCode();
    }

    public String getCompletedExerciseId() {
        return completedExerciseId;
    }

    public void setCompletedExerciseId(String completedExerciseId) {
        this.completedExerciseId = completedExerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public BigDecimal getExerciseDuration() {
        return exerciseDuration;
    }

    public void setExerciseDuration(BigDecimal exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }

    public BigDecimal getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(BigDecimal burnedCalories) {
        this.burnedCalories = burnedCalories;
    }
}
