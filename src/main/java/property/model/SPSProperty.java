package property.model;

import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "SPS_PROPERTY")
public class SPSProperty {

    @Id
    @Column(name = "PROP_KEY", length = 128, nullable = false)
    @Index(name = "sps_prop_key_index")
    private String propKey;

    @Column(name = "PROP_VALUE", length = 1024, nullable = false)
    @Index(name = "sps_prop_value_index")
    private String propValue;

    public String getPropKey() {
        return propKey;
    }

    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    public String getPropValue() {
        return propValue;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    @Override
    public String toString() {
        return "SPSProperty [propKey=" + propKey + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((propKey == null) ? 0 : propKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SPSProperty other = (SPSProperty) obj;
        if (propKey == null) {
            if (other.propKey != null)
                return false;
        } else if (!propKey.equals(other.propKey))
            return false;
        return true;
    }

}
