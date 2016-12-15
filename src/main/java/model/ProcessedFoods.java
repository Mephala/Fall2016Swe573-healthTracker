package model;

import persistance.USFoodInfoCard;

import java.util.List;

/**
 * Created by Mephalay on 12/15/2016.
 */
public class ProcessedFoods {
    private List<USFoodInfoCard> foods;

    public List<USFoodInfoCard> getFoods() {
        return foods;
    }

    public void setFoods(List<USFoodInfoCard> foods) {
        this.foods = foods;
    }
}
