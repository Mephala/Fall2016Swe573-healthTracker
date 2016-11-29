package model;

import java.util.List;

/**
 * Created by Mephalay on 11/29/2016.
 */
public class CalorieGraphResponse {

    private Boolean loginSuccess = Boolean.FALSE; // set to true when successful, default is false.
    private Boolean dataFound = Boolean.TRUE;
    private List<GraphResponse> graphResponseList;


    public List<GraphResponse> getGraphResponseList() {
        return graphResponseList;
    }

    public void setGraphResponseList(List<GraphResponse> graphResponseList) {
        this.graphResponseList = graphResponseList;
    }

    public Boolean getLoginSuccess() {
        return loginSuccess;
    }

    public void setLoginSuccess(Boolean loginSuccess) {
        this.loginSuccess = loginSuccess;
    }

    public Boolean getDataFound() {
        return dataFound;
    }

    public void setDataFound(Boolean dataFound) {
        this.dataFound = dataFound;
    }
}
