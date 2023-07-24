package eu.chargetime.ocpp.jsonserverimplementation.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.DateTime;

/**
 * @author ket_ein17
 */
public final class OcppTag {
    @Getter
    @Builder
    @ToString
    public static final class Overview {
        private final Integer ocppTagPk;
        private final String idTag;

        private final Integer parentOcppTagPk;
        private final String parentIdTag;

        private final boolean inTransaction;
        private final boolean blocked;

        /**
         * Only relevant for the web pages. Disabled for API
         */
        @JsonIgnore
        @ApiModelProperty(hidden = true)
        private final String expiryDateFormatted;

        private final DateTime expiryDate;

        private final Integer maxActiveTransactionCount;
        private final Long activeTransactionCount;
        private final String note;
    }
}
