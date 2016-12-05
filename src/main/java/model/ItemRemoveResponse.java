package model;

/**
 * Created by Mephalay on 12/5/2016.
 */
public class ItemRemoveResponse {

    private Boolean completed;
    private String promptMsg;

    @Override
    public String toString() {
        return "ItemRemoveResponse{" +
                "completed=" + completed +
                ", promptMsg='" + promptMsg + '\'' +
                '}';
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getPromptMsg() {
        return promptMsg;
    }

    public void setPromptMsg(String promptMsg) {
        this.promptMsg = promptMsg;
    }
}
