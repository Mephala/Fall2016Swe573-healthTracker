
package model;

import org.codehaus.jackson.annotate.*;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;


@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "report"
})
public class FoodNutritionResponse {

    @Override
    public String toString() {
        return "FoodNutritionResponse{" +
                "report=" + report +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @JsonProperty("report")
    private Report report;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The report
     */
    @JsonProperty("report")
    public Report getReport() {
        return report;
    }

    /**
     * 
     * @param report
     *     The report
     */
    @JsonProperty("report")
    public void setReport(Report report) {
        this.report = report;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
