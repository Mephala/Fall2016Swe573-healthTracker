package util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Mephalay on 10/25/2016.
 */
public class CommonUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

    public static String normalizeHttpUrlString(String urlString) {
        if (CommonUtils.isEmpty(urlString))
            return "";
        urlString = urlString.replaceAll("\\+", " ");
        urlString = urlString.replaceAll("%28", "(");
        urlString = urlString.replaceAll("%29", ")");
        urlString = urlString.replaceAll("%2C", ",");
        urlString = urlString.replaceAll("%2C", ",");
        return urlString;
    }

    public static String formatCurrentDate() {
        return sdf.format(new Date());
    }

    public static boolean isValidDate(String date) {
        if (isEmpty(date))
            return false;
        try {
            sdf.parse(date);
            return true;
        } catch (Throwable t) {
            return false;
        }
    }


    public static <E> Set<E> convertListToSet(List<E> list) {
        if (list == null)
            return null;
        Set<E> set = new HashSet<>();
        for (E e : list) {
            set.add(e);
        }
        return set;
    }

    public static <E> List<E> convertSetToList(Set<E> set) {
        if (set == null)
            return null;
        List<E> list = new ArrayList<>();
        for (E e : set) {
            list.add(e);
        }
        return list;
    }

    public static Date parseToDate(String age) throws ParseException {
        return sdf.parse(age);
    }
}
