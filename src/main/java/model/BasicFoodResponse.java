package model;

/**
 * Created by Mephalay on 10/25/2016.
 */
public class BasicFoodResponse {

    private String title;
    private String json;
    private String hrefId;

    public String getHrefId() {
        return hrefId;
    }

    public void setHrefId(String hrefId) {
        this.hrefId = hrefId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
