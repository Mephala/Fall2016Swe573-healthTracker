package util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mephalay on 11/21/2016.
 */
public class ControllerUtils {

    public static Map<String, String> convertRequestBodyToMap(String reqBody) {
        if (CommonUtils.isEmpty(reqBody))
            return new HashMap<>();
        String[] params = reqBody.split("&");
        Map<String, String> retval = new HashMap<>();
        for (String param : params) {
            if (CommonUtils.isEmpty(param))
                continue;
            String[] kvDuple = param.split("=");
            if (kvDuple == null || kvDuple.length < 2)
                continue;
            String key = kvDuple[0];
            String val = kvDuple[1];
            key = CommonUtils.normalizeHttpUrlString(key);
            val = CommonUtils.normalizeHttpUrlString(val);
            retval.put(key, val);
        }
        return retval;
    }
}
