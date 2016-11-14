package model;

import java.util.List;
import java.util.Map;

/**
 * Created by Mephalay on 10/29/2016.
 */
public class AjaxSearchResponse {

    private List<String> availableKeywords;
    private Map<String, String> unitMap;

    public Map<String, String> getUnitMap() {
        return unitMap;
    }

    public void setUnitMap(Map<String, String> unitMap) {
        this.unitMap = unitMap;
    }

    public List<String> getAvailableKeywords() {
        return availableKeywords;
    }

    public void setAvailableKeywords(List<String> availableKeywords) {
        this.availableKeywords = availableKeywords;
    }
}
