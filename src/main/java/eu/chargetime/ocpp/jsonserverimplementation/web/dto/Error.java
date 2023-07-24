package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {
    private String errorMessage;
    public Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
