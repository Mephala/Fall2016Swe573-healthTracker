package util;

import java.math.BigDecimal;

/**
 * Created by Mephalay on 10/30/2016.
 */
public enum Gender {

    MALE(66, new BigDecimal("13.7"), new BigDecimal("5"), new BigDecimal("6.8")), FEMALE(655, new BigDecimal("9.6"), new BigDecimal("1.8"), new BigDecimal("4.7"));

    private Integer baseCoefficient;
    private BigDecimal weightCoefficient;
    private BigDecimal heightCoefficient;
    private BigDecimal ageCoefficient;


    Gender(Integer baseCoefficient, BigDecimal weightCoefficient, BigDecimal heightCoefficient, BigDecimal ageCoefficient) {
        this.baseCoefficient = baseCoefficient;
        this.weightCoefficient = weightCoefficient;
        this.heightCoefficient = heightCoefficient;
        this.ageCoefficient = ageCoefficient;
    }

    public BigDecimal getWeightCoefficient() {
        return weightCoefficient;
    }

    public BigDecimal getHeightCoefficient() {
        return heightCoefficient;
    }

    public BigDecimal getAgeCoefficient() {
        return ageCoefficient;
    }

    public Integer getBaseCoefficient() {
        return baseCoefficient;
    }


    public static Gender forName(String gender) {
        if ("Male".equals(gender))
            return MALE;
        return FEMALE;
    }
}
