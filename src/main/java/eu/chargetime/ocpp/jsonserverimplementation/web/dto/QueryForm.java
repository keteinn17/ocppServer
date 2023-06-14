package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.AssertTrue;

@Getter
@Setter
@NoArgsConstructor
@ToString
public abstract class QueryForm {
    @ApiModelProperty(value = "The identifier of the chargebox (i.e. charging station)")
    private String chargeBoxId;

    @ApiModelProperty(value = "The OCPP tag")
    private String ocppIdTag;

    @ApiModelProperty(value = "Show results that happened after this date/time. Format: ISO8601 without timezone. Example: `2022-10-10T09:00:00`")
    private LocalDateTime from;

    @ApiModelProperty(value = "Show results that happened before this date/time. Format: ISO8601 without timezone. Example: `2022-10-10T12:00:00`")
    private LocalDateTime to;

    @ApiModelProperty(hidden = true)
    @AssertTrue(message = "'To' must be after 'From'")
    public boolean isFromToValid() {
        return !isFromToSet() || to.isAfter(from);
    }

    @ApiModelProperty(hidden = true)
    boolean isFromToSet() {
        return from != null && to != null;
    }

    @ApiModelProperty(hidden = true)
    public boolean isChargeBoxIdSet() {
        return chargeBoxId != null;
    }

    @ApiModelProperty(hidden = true)
    public boolean isOcppIdTagSet() {
        return ocppIdTag != null;
    }
}
