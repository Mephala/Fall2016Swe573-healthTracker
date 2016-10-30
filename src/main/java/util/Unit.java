package util;

/**
 * Created by Mephalay on 10/30/2016.
 */
public enum Unit {
    METRIC, IMPERIAL;

    public static Unit forName(String weightSelect) {
        if ("kilo".equals(weightSelect) || "cm".equals(weightSelect)) {
            return METRIC;
        } else {
            return IMPERIAL;
        }
    }
}
