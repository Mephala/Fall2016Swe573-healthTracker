package model;


import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mephalay on 10/28/2016.
 */
@JsonPropertyOrder({
        "context",
        "id",
        "quote",
        "ranges",
        "text",
        "target"
})
public class IdentifiedAnnotationObject {


    @JsonProperty(value = "@context")
    private String context = "http://www.w3.org/ns/anno.jsonld";
    private String id;
    private String quote;
    private List<Range> ranges = new ArrayList<Range>();
    private String text;
    @JsonProperty(value = "target")
    private AnnotationTarget target;


    public AnnotationTarget getTarget() {
        return target;
    }

    public void setTarget(AnnotationTarget target) {
        this.target = target;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public List<Range> getRanges() {
        return ranges;
    }

    public void setRanges(List<Range> ranges) {
        this.ranges = ranges;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
