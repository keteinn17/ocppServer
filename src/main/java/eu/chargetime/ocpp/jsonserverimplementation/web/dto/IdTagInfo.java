package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import eu.chargetime.ocpp.jsonserverimplementation.utils.JodaDateTimeConverter;
import eu.chargetime.ocpp.model.core.AuthorizationStatus;
import org.joda.time.DateTime;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author ket_ein17
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "IdTagInfo",
        propOrder = {"status", "expiryDate", "parentIdTag"}
)
public class IdTagInfo {
    @XmlElement(
            required = true
    )
    @XmlSchemaType(
            name = "string"
    )
    protected AuthorizationStatus status;
    @XmlElement(
            type = String.class
    )
    @XmlJavaTypeAdapter(JodaDateTimeConverter.class)
    @XmlSchemaType(
            name = "dateTime"
    )
    protected DateTime expiryDate;
    protected String parentIdTag;

    public IdTagInfo() {
    }

    public AuthorizationStatus getStatus() {
        return this.status;
    }

    public void setStatus(AuthorizationStatus value) {
        this.status = value;
    }

    public boolean isSetStatus() {
        return this.status != null;
    }

    public DateTime getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(DateTime value) {
        this.expiryDate = value;
    }

    public boolean isSetExpiryDate() {
        return this.expiryDate != null;
    }

    public String getParentIdTag() {
        return this.parentIdTag;
    }

    public void setParentIdTag(String value) {
        this.parentIdTag = value;
    }

    public boolean isSetParentIdTag() {
        return this.parentIdTag != null;
    }

    public IdTagInfo withStatus(AuthorizationStatus value) {
        this.setStatus(value);
        return this;
    }

    public IdTagInfo withExpiryDate(DateTime value) {
        this.setExpiryDate(value);
        return this;
    }

    public IdTagInfo withParentIdTag(String value) {
        this.setParentIdTag(value);
        return this;
    }

    public String toString() {
        return "IdTagInfo(status=" + this.getStatus() + ", expiryDate=" + this.getExpiryDate() + ", parentIdTag=" + this.getParentIdTag() + ")";
    }
}
