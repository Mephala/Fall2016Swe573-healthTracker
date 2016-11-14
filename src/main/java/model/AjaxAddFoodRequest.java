package model;

/**
 * Created by Mephalay on 10/30/2016.
 */
public class AjaxAddFoodRequest {
    private String addedFood;
    private String amount;

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
