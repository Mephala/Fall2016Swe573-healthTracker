package util;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Mephalay on 10/25/2016.
 */
public class CommonUtils {

    public static boolean isEmpty(String s) {
        return (s == null || s.isEmpty());
    }

    public static boolean notEmpty(String s) {
        return !isEmpty(s);
    }


    public static boolean isEmpty(List l) {
        return (l == null || l.size() == 0);
    }

    public static boolean notEmpty(List l) {
        return !isEmpty(l);
    }

    public static boolean isNumeric(String s) {
        try {
            new BigDecimal(s);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }


}
