package model;

import java.util.List;

/**
 * Created by Mephalay on 10/28/2016.
 */
public class AnnotationSearchResult {

    private Integer total;
    private List<IdentifiedAnnotationObject> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<IdentifiedAnnotationObject> getRows() {
        return rows;
    }

    public void setRows(List<IdentifiedAnnotationObject> rows) {
        this.rows = rows;
    }
}
