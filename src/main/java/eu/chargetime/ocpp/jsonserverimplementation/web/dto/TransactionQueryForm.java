package eu.chargetime.ocpp.jsonserverimplementation.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

/**
 * @author ket_ein17
 */
@Getter
@Setter
@ToString(callSuper = true)
public class TransactionQueryForm extends QueryForm{
    // Internal database Id
    @ApiModelProperty(value = "Database primary key of the transaction")
    private Integer transactionPk;

    @ApiModelProperty(value = "Disabled for the Web APIs. Do not use and set", hidden = true)
    private boolean returnCSV = false;

    @ApiModelProperty(value = "Return active or all transactions? Defaults to ALL")
    private QueryType type = QueryType.ACTIVE;

    @ApiModelProperty(value = "Return the time period of the transactions. If FROM_TO, 'from' and 'to' must be set. Additionally, 'to' must be after 'from'. Defaults to ALL")
    private QueryPeriodType periodType = QueryPeriodType.ALL;

    @ApiModelProperty(hidden = true)
    @AssertTrue(message = "The values 'From' and 'To' must be both set")
    public boolean isPeriodFromToCorrect() {
        return periodType != QueryPeriodType.FROM_TO || isFromToSet();
    }

    @ApiModelProperty(hidden = true)
    public boolean isTransactionPkSet() {
        return transactionPk != null;
    }

    public QueryType getType() {
        return Objects.requireNonNullElse(type, QueryType.ALL);
    }

    public QueryPeriodType getPeriodType() {
        return Objects.requireNonNullElse(periodType, QueryPeriodType.ALL);
    }

    // -------------------------------------------------------------------------
    // Enums
    // -------------------------------------------------------------------------

    @RequiredArgsConstructor
    public enum QueryType {
        ALL("All"),
        ACTIVE("Active");

        @Getter private final String value;

        public static QueryType fromValue(String v) {
            for (QueryType c: QueryType.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }
    }

    @RequiredArgsConstructor
    public enum QueryPeriodType {
        ALL("All", -1),
        TODAY("Today", -1),
        LAST_10("Last 10 days", 10),
        LAST_30("Last 30 days", 30),
        LAST_90("Last 90 days", 90),
        FROM_TO("From/To", -1);

        @Getter private final String value;
        private final int interval;

        public int getInterval() {
            if (this.interval == -1) {
                throw new UnsupportedOperationException("This enum does not have any meaningful interval set.");
            }
            return this.interval;
        }

        public static QueryPeriodType fromValue(String v) {
            for (QueryPeriodType c: QueryPeriodType.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }
    }

    @ToString(callSuper = true)
    public static class ForApi extends TransactionQueryForm {

        public ForApi() {
            super();
            setType(QueryType.ALL);
            setPeriodType(QueryPeriodType.ALL);
        }
    }
}
