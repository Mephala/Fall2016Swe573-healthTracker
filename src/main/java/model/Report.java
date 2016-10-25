
package model;

import org.codehaus.jackson.annotate.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;


@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "sr",
    "type",
    "food",
    "footnotes"
})
public class Report {

    @JsonProperty("sr")
    private String sr;
    @JsonProperty("type")
    private String type;
    @JsonProperty("food")
    private Food food;
    @JsonProperty("footnotes")
    private List<Object> footnotes = new ArrayList<Object>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The sr
     */
    @JsonProperty("sr")
    public String getSr() {
        return sr;
    }

    /**
     * 
     * @param sr
     *     The sr
     */
    @JsonProperty("sr")
    public void setSr(String sr) {
        this.sr = sr;
    }

    /**
     * 
     * @return
     *     The type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The food
     */
    @JsonProperty("food")
    public Food getFood() {
        return food;
    }

    /**
     * 
     * @param food
     *     The food
     */
    @JsonProperty("food")
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * 
     * @return
     *     The footnotes
     */
    @JsonProperty("footnotes")
    public List<Object> getFootnotes() {
        return footnotes;
    }

    /**
     * 
     * @param footnotes
     *     The footnotes
     */
    @JsonProperty("footnotes")
    public void setFootnotes(List<Object> footnotes) {
        this.footnotes = footnotes;
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
        return "Report{" +
                "sr='" + sr + '\'' +
                ", type='" + type + '\'' +
                ", food=" + food +
                ", footnotes=" + footnotes +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
