package persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by Mephalay on 11/27/2016.
 */
@Entity(name = "EATEN_FOOD")
public class EatenFood {

    @Id
    @Column(name = "EATEN_FOOD_ID")
    private String eatenFoodId;

    @Column(name = "NBDBNO")
    private String nbdbno;

    @Column(name = "CONSUMED_AMOUNT")
    private String amount;

    @Column(name = "CONSUMED_UNIT")
    private String unit;

    @Column(name = "CALORIE_INTAKE")
    private BigDecimal consumedCalorie;

    @Override
    public String toString() {
        return "EatenFood{" +
                "nbdbno='" + nbdbno + '\'' +
                ", eatenFoodId='" + eatenFoodId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EatenFood eatenFood = (EatenFood) o;

        return eatenFoodId.equals(eatenFood.eatenFoodId);

    }

    @Override
    public int hashCode() {
        return eatenFoodId.hashCode();
    }

    public String getEatenFoodId() {
        return eatenFoodId;
    }

    public void setEatenFoodId(String eatenFoodId) {
        this.eatenFoodId = eatenFoodId;
    }

    public String getNbdbno() {
        return nbdbno;
    }

    public void setNbdbno(String nbdbno) {
        this.nbdbno = nbdbno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getConsumedCalorie() {
        return consumedCalorie;
    }

    public void setConsumedCalorie(BigDecimal consumedCalorie) {
        this.consumedCalorie = consumedCalorie;
    }
}
