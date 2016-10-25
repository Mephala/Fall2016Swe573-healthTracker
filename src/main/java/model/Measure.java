
package model;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;


@Generated("org.jsonschema2pojo")

public class Measure {

    @JsonProperty("label")
    private String label;
    @JsonProperty("eqv")
    private Integer eqv;
    @JsonProperty("qty")
    private Integer qty;
    @JsonProperty("value")
    private String value;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The label
     */
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return
     *     The eqv
     */
    @JsonProperty("eqv")
    public Integer getEqv() {
        return eqv;
    }

    /**
     * 
     * @param eqv
     *     The eqv
     */
    @JsonProperty("eqv")
    public void setEqv(Integer eqv) {
        this.eqv = eqv;
    }

    /**
     * 
     * @return
     *     The qty
     */
    @JsonProperty("qty")
    public Integer getQty() {
        return qty;
    }

    /**
     * 
     * @param qty
     *     The qty
     */
    @JsonProperty("qty")
    public void setQty(Integer qty) {
        this.qty = qty;
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
        return "Measure{" +
                "label='" + label + '\'' +
                ", eqv=" + eqv +
                ", qty=" + qty +
                ", value='" + value + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
