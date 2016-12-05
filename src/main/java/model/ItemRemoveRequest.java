package model;

/**
 * Created by Mephalay on 12/5/2016.
 */
public class ItemRemoveRequest {
    private String date;
    private String itemId;

    @Override
    public String toString() {
        return "ItemRemoveRequest{" +
                "date='" + date + '\'' +
                ", itemId='" + itemId + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
