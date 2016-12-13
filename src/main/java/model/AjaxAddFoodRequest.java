package model;

/**
 * Created by Mephalay on 10/30/2016.
 */
public class AjaxAddFoodRequest {
    private String addedFood;
    private String amount;
    private String date;
    private String unit;

    @Override
    public String toString() {
        return "AjaxAddFoodRequest{" +
                "addedFood='" + addedFood + '\'' +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddedFood() {
        return addedFood;
    }

    public void setAddedFood(String addedFood) {
        this.addedFood = addedFood;
    }
}
