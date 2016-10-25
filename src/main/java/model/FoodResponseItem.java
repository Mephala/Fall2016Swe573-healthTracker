
package model;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.HashMap;
import java.util.Map;


public class FoodResponseItem {

    @Override
    public String toString() {
        return "FoodResponseItem{" +
                "offset=" + offset +
                ", group='" + group + '\'' +
                ", name='" + name + '\'' +
                ", ndbno='" + ndbno + '\'' +
                ", ds='" + ds + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @JsonProperty("offset")
    private Integer offset;
    @JsonProperty("group")
    private String group;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ndbno")
    private String ndbno;
    @JsonProperty("ds")
    private String ds;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The offset
     */
    @JsonProperty("offset")
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param offset The offset
     */
    @JsonProperty("offset")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @return The group
     */
    @JsonProperty("group")
    public String getGroup() {
        return group;
    }

    /**
     * @param group The group
     */
    @JsonProperty("group")
    public void setGroup(String group) {
        this.group = group;
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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
