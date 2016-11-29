package model;

/**
 * Created by Mephalay on 11/29/2016.
 */
public class GraphResponse {

    private Integer calorieBurned;
    private Integer calorieTaken;
    private Integer dailyCalorieNeed;
    private Integer day;
    private Integer month;
    private Integer year;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCalorieBurned() {
        return calorieBurned;
    }

    public void setCalorieBurned(Integer calorieBurned) {
        this.calorieBurned = calorieBurned;
    }

    public Integer getCalorieTaken() {
        return calorieTaken;
    }

    public void setCalorieTaken(Integer calorieTaken) {
        this.calorieTaken = calorieTaken;
    }

    public Integer getDailyCalorieNeed() {
        return dailyCalorieNeed;
    }

    public void setDailyCalorieNeed(Integer dailyCalorieNeed) {
        this.dailyCalorieNeed = dailyCalorieNeed;
    }
}
