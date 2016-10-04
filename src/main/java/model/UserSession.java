package model;

/**
 * Created by Mephalay on 10/2/2016.
 */
public class UserSession {
    private Boolean login = Boolean.FALSE;
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }
}
