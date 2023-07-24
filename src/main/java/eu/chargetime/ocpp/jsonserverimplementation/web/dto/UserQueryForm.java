package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ket_ein17
 */
@Getter
@Setter
@ToString
public class UserQueryForm {
    private Integer userPk;

    // Free text input
    private String ocppIdTag;
    private String name;
    private String email;

    public boolean isSetUserPk() {
        return userPk != null;
    }

    public boolean isSetOcppIdTag() {
        return ocppIdTag != null;
    }

    public boolean isSetName() {
        return name != null;
    }

    public boolean isSetEmail() {
        return email != null;
    }
}
