package model;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FoodResponseList {


    @Override
    public String toString() {
        return "FoodResponseList{" +
                "q='" + q + '\'' +
                ", sr='" + sr + '\'' +
                ", ds='" + ds + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", total=" + total +
                ", group='" + group + '\'' +
                ", sort='" + sort + '\'' +
                ", item=" + item +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @JsonProperty("q")
    private String q;
    @JsonProperty("sr")
    private String sr;
    @JsonProperty("ds")
    private String ds;
    @JsonProperty("start")
    private Integer start;
    @JsonProperty("end")
    private Integer end;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("group")
    private String group;
    @JsonProperty("sort")
    private String sort;
    @JsonProperty("item")
    private java.util.List<FoodResponseItem> item = new ArrayList<FoodResponseItem>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The q
     */
    @JsonProperty("q")
    public String getQ() {
        return q;
    }

    /**
     * @param q The q
     */
    @JsonProperty("q")
    public void setQ(String q) {
        this.q = q;
    }

    /**
     * @return The sr
     */
    @JsonProperty("sr")
    public String getSr() {
        return sr;
    }

    /**
     * @param sr The sr
     */
    @JsonProperty("sr")
    public void setSr(String sr) {
        this.sr = sr;
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
     * @return The start
     */
    @JsonProperty("start")
    public Integer getStart() {
        return start;
    }

    /**
     * @param start The start
     */
    @JsonProperty("start")
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * @return The end
     */
    @JsonProperty("end")
    public Integer getEnd() {
        return end;
    }

    /**
     * @param end The end
     */
    @JsonProperty("end")
    public void setEnd(Integer end) {
        this.end = end;
    }

    /**
     * @return The total
     */
    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
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
     * @return The sort
     */
    @JsonProperty("sort")
    public String getSort() {
        return sort;
    }

    /**
     * @param sort The sort
     */
    @JsonProperty("sort")
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * @return The item
     */
    @JsonProperty("item")
    public java.util.List<FoodResponseItem> getItem() {
        return item;
    }

    /**
     * @param item The item
     */
    @JsonProperty("item")
    public void setItem(java.util.List<FoodResponseItem> item) {
        this.item = item;
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
