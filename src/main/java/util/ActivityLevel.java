package util;

import java.math.BigDecimal;

/**
 * Created by Mephalay on 10/30/2016.
 */
public enum ActivityLevel {
    SEDENTARY(new BigDecimal("1.2")),
    LIGHTY_ACTIVE(new BigDecimal("1.375")),
    MODERATELY_ACTIVE(new BigDecimal("1.55")),
    VERY_ACTIVE(new BigDecimal("1.725")),
    EXTRA_ACTIVE(new BigDecimal("1.9"));

    private BigDecimal coefficient;

    ActivityLevel(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }

    protected BigDecimal getCoefficient() {
        return this.coefficient;
    }


}
