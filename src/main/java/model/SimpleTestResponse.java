package model;

/**
 * Created by Mephalay on 11/18/2016.
 */
public class SimpleTestResponse {

    private String responseName;
    private String responseSecondParameter;

    public SimpleTestResponse() {

    }

    public SimpleTestResponse(String responseName, String responseSecondParameter) {
        this.responseName = responseName;
        this.responseSecondParameter = responseSecondParameter;
    }

    public String getResponseName() {
        return responseName;
    }

    public void setResponseName(String responseName) {
        this.responseName = responseName;
    }

    public String getResponseSecondParameter() {
        return responseSecondParameter;
    }

    public void setResponseSecondParameter(String responseSecondParameter) {
        this.responseSecondParameter = responseSecondParameter;
    }
}
