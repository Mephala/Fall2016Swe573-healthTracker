
package model;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;


@Generated("org.jsonschema2pojo")

public class Nutrient {

    @JsonProperty("nutrient_id")
    private String nutrientId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("group")
    private String group;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("value")
    private String value;
    @JsonProperty("measures")
    private List<Measure> measures = new ArrayList<Measure>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The nutrientId
     */
    @JsonProperty("nutrient_id")
    public String getNutrientId() {
        return nutrientId;
    }

    /**
     * 
     * @param nutrientId
     *     The nutrient_id
     */
    @JsonProperty("nutrient_id")
    public void setNutrientId(String nutrientId) {
        this.nutrientId = nutrientId;
    }

    /**
     * 
     * @return
     *     The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The group
     */
    @JsonProperty("group")
    public String getGroup() {
        return group;
    }

    /**
     * 
     * @param group
     *     The group
     */
    @JsonProperty("group")
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * 
     * @return
     *     The unit
     */
    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    /**
     * 
     * @param unit
     *     The unit
     */
    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * 
     * @return
     *     The value
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 
     * @return
     *     The measures
     */
    @JsonProperty("measures")
    public List<Measure> getMeasures() {
        return measures;
    }

    /**
     * 
     * @param measures
     *     The measures
     */
    @JsonProperty("measures")
    public void setMeasures(List<Measure> measures) {
        this.measures = measures;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Nutrient{" +
                "nutrientId='" + nutrientId + '\'' +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", unit='" + unit + '\'' +
                ", value='" + value + '\'' +
                ", measures=" + measures +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
