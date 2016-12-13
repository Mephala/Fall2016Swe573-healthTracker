package persistance;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Mephalay on 10/29/2016.
 */
@Entity(name = "US_FOOD_INFO_CARD")
public class USFoodInfoCard {

    @Id
    @Column(name = "NDBNO")
    private String ndbno;

    @Column(name = "FOOD_NAME")
    private String foodName;


    @Column(name = "GROUP_NAME")
    private String groupName;


    @Column(name = "DS_VAL")
    private String dsVal;

    @OneToMany(cascade = CascadeType.ALL)
    private List<PersistedNutrition> persistedNutritionList;


    public List<PersistedNutrition> getPersistedNutritionList() {
        return persistedNutritionList;
    }

    public void setPersistedNutritionList(List<PersistedNutrition> persistedNutritionList) {
        this.persistedNutritionList = persistedNutritionList;
    }

    @Override
    public String toString() {
        return "USFoodInfoCard{" +
                "foodName='" + foodName + '\'' +
                '}';
    }

    public String getNdbno() {
        return ndbno;
    }

    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDsVal() {
        return dsVal;
    }

    public void setDsVal(String dsVal) {
        this.dsVal = dsVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        USFoodInfoCard that = (USFoodInfoCard) o;

        return ndbno.equals(that.ndbno);

    }

    @Override
    public int hashCode() {
        return ndbno.hashCode();
    }
}
