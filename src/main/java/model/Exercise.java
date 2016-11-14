package model;

/**
 * Created by Mephalay on 11/14/2016.
 */
public class Exercise {

    private String type;
    //For other weight types it increases linearly.
    private Integer calorie130lbs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        return type.equals(exercise.type);

    }

    @Override
    public String toString() {
        return "Exercise{" +
                "type='" + type + '\'' +
                ", calorie130lbs=" + calorie130lbs +
                '}';
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCalorie130lbs() {
        return calorie130lbs;
    }

    public void setCalorie130lbs(Integer calorie130lbs) {
        this.calorie130lbs = calorie130lbs;
    }
}
