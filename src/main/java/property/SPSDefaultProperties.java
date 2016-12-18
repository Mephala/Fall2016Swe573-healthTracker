package property;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SPSDefaultProperties {

    private static Map<String, String> defPropMap = new HashMap<>();

    static {
        //namazApp config
        defPropMap.put("server.ENV", "remote");


    }

    public static Map<String, String> getDefaultProps() {
        Map<String, String> retval = new HashMap<>();
        Set<String> keySet = defPropMap.keySet();
        for (String key : keySet) {
            retval.put(key, defPropMap.get(key));
        }
        return retval;
    }

}
