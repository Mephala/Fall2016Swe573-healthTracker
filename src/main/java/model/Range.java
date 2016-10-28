
package model;

import org.codehaus.jackson.annotate.*;

import java.util.HashMap;
import java.util.Map;


@JsonPropertyOrder({
        "start",
        "startOffset",
        "end",
        "endOffset"
})
public class Range {

    @Override
    public String toString() {
        return "Range{" +
                "start='" + start + '\'' +
                ", startOffset=" + startOffset +
                ", end='" + end + '\'' +
                ", endOffset=" + endOffset +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @JsonProperty("start")
    private String start;
    @JsonProperty("startOffset")
    private Integer startOffset;
    @JsonProperty("end")
    private String end;
    @JsonProperty("endOffset")
    private Integer endOffset;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The start
     */
    @JsonProperty("start")
    public String getStart() {
        return start;
    }

    /**
     * @param start The start
     */
    @JsonProperty("start")
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * @return The startOffset
     */
    @JsonProperty("startOffset")
    public Integer getStartOffset() {
        return startOffset;
    }

    /**
     * @param startOffset The startOffset
     */
    @JsonProperty("startOffset")
    public void setStartOffset(Integer startOffset) {
        this.startOffset = startOffset;
    }

    /**
     * @return The end
     */
    @JsonProperty("end")
    public String getEnd() {
        return end;
    }

    /**
     * @param end The end
     */
    @JsonProperty("end")
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * @return The endOffset
     */
    @JsonProperty("endOffset")
    public Integer getEndOffset() {
        return endOffset;
    }

    /**
     * @param endOffset The endOffset
     */
    @JsonProperty("endOffset")
    public void setEndOffset(Integer endOffset) {
        this.endOffset = endOffset;
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
