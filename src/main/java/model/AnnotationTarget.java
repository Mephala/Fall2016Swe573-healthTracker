package model;

/**
 * Created by Mephalay on 10/28/2016.
 */
public class AnnotationTarget {

    private String id;
    private String type = "Text";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
