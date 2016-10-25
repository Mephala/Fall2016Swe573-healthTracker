package util;

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
}
