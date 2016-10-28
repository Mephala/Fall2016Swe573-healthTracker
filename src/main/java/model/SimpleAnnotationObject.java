
package model;

import org.codehaus.jackson.annotate.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonPropertyOrder({
        "quote",
        "ranges",
        "text"
})
public class SimpleAnnotationObject {

    @JsonProperty("quote")
    private String quote;
    @JsonProperty("ranges")
    private List<Range> ranges = new ArrayList<Range>();
    @JsonProperty("text")
    private String text;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The quote
     */
    @JsonProperty("quote")
    public String getQuote() {
        return quote;
    }

    /**
     * @param quote The quote
     */
    @JsonProperty("quote")
    public void setQuote(String quote) {
        this.quote = quote;
    }

    /**
     * @return The ranges
     */
    @JsonProperty("ranges")
    public List<Range> getRanges() {
        return ranges;
    }

    /**
     * @param ranges The ranges
     */
    @JsonProperty("ranges")
    public void setRanges(List<Range> ranges) {
        this.ranges = ranges;
    }

    /**
     * @return The text
     */
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    /**
     * @param text The text
     */
    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public String toString() {
        return "SimpleAnnotationObject{" +
                "additionalProperties=" + additionalProperties +
                ", text='" + text + '\'' +
                ", ranges=" + ranges +
                ", quote='" + quote + '\'' +
                '}';
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
