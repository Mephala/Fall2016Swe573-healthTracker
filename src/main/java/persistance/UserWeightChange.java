package persistance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Mephalay on 11/20/2016.
 */
@Entity(name = "USER_WEIGHT_CHANGE")
public class UserWeightChange {

    @Id
    @Column(name = "CHANGE_ID")
    private String changeId;

    @Column(name = "PREVIOUS_WEIGHT", nullable = false)
    private BigDecimal previousWeight;

    @Column(name = "CURRENT_WEIGHT", nullable = false)
    private BigDecimal currentWeight;

    @Column(name = "CHANGE_VALUE")
    private BigDecimal weightChange;

    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "CHANGE_TIME", nullable = false)
    private Date changeTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserWeightChange that = (UserWeightChange) o;

        return changeId.equals(that.changeId);

    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return changeId.hashCode();
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    public BigDecimal getPreviousWeight() {
        return previousWeight;
    }

    public void setPreviousWeight(BigDecimal previousWeight) {
        this.previousWeight = previousWeight;
    }

    public BigDecimal getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(BigDecimal currentWeight) {
        this.currentWeight = currentWeight;
    }

    public BigDecimal getWeightChange() {
        return weightChange;
    }

    public void setWeightChange(BigDecimal weightChange) {
        this.weightChange = weightChange;
    }
}
