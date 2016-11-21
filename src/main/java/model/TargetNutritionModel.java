package model;

/**
 * Created by Mephalay on 11/21/2016.
 */
public class TargetNutritionModel {

    private String name;
    private String unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TargetNutritionModel that = (TargetNutritionModel) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "TargetNutritionModel{" +
                "name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
