package eu.chargetime.ocpp.jsonserverimplementation.utils;

import org.joda.time.LocalDate;
import org.jooq.Converter;

import java.sql.Date;

public class DateConverter implements Converter<Date, LocalDate> {
    @Override
    public LocalDate from(Date sqlDate) {
        if (sqlDate == null) {
            return null;
        } else {
            return new LocalDate(sqlDate.getTime());
        }
    }

    @Override
    public Date to(LocalDate jodaDate) {
        if (jodaDate == null) {
            return null;
        } else {
            return new Date(jodaDate.toDateTimeAtStartOfDay().getMillis());
        }
    }

    @Override
    public Class<Date> fromType() {
        return Date.class;
    }

    @Override
    public Class<LocalDate> toType() {
        return LocalDate.class;
    }
}
