
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

public class Food {

    @JsonProperty("ndbno")
    private String ndbno;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ds")
    private String ds;
    @JsonProperty("nutrients")
    private List<Nutrient> nutrients = new ArrayList<Nutrient>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The ndbno
     */
    @JsonProperty("ndbno")
    public String getNdbno() {
        return ndbno;
    }

    /**
     * @param ndbno The ndbno
     */
    @JsonProperty("ndbno")
    public void setNdbno(String ndbno) {
        this.ndbno = ndbno;
    }

    /**
     * @return The name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The ds
     */
    @JsonProperty("ds")
    public String getDs() {
        return ds;
    }

    /**
     * @param ds The ds
     */
    @JsonProperty("ds")
    public void setDs(String ds) {
        this.ds = ds;
    }

    /**
     * @return The nutrients
     */
    @JsonProperty("nutrients")
    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    /**
     * @param nutrients The nutrients
     */
    @JsonProperty("nutrients")
    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
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
        return "Food{" +
                "ndbno='" + ndbno + '\'' +
                ", name='" + name + '\'' +
                ", ds='" + ds + '\'' +
                ", nutrients=" + nutrients +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
