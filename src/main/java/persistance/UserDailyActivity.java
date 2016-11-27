package persistance;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Mephalay on 11/27/2016.
 */
@Entity(name = "USER_DAILY_ACTIVITY")
public class UserDailyActivity {

    @Id
    @Column(name = "DAILY_ACTIVITY_ID")
    private String dailyActivityId;

    @Column(name = "ACTIVITY_DATE")
    private String activityDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<UserCompletedExercise> userCompletedExercises;

    @OneToMany(cascade = CascadeType.ALL)
    private List<EatenFood> userEatenFood;

    public String getDailyActivityId() {
        return dailyActivityId;
    }

    public void setDailyActivityId(String dailyActivityId) {
        this.dailyActivityId = dailyActivityId;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public List<UserCompletedExercise> getUserCompletedExercises() {
        return userCompletedExercises;
    }

    public void setUserCompletedExercises(List<UserCompletedExercise> userCompletedExercises) {
        this.userCompletedExercises = userCompletedExercises;
    }

    public List<EatenFood> getUserEatenFood() {
        return userEatenFood;
    }

    public void setUserEatenFood(List<EatenFood> userEatenFood) {
        this.userEatenFood = userEatenFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDailyActivity that = (UserDailyActivity) o;

        return dailyActivityId.equals(that.dailyActivityId);

    }

    @Override
    public int hashCode() {
        return dailyActivityId.hashCode();
    }
}
