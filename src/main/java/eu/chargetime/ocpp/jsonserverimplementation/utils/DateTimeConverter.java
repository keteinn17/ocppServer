package eu.chargetime.ocpp.jsonserverimplementation.utils;

import org.joda.time.DateTime;
import org.jooq.Converter;

import java.sql.Timestamp;

public class DateTimeConverter implements Converter<Timestamp, DateTime> {
    @Override
    public DateTime from(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        } else {
            return new DateTime(timestamp.getTime());
        }
    }

    @Override
    public Timestamp to(DateTime dateTime) {
        if (dateTime == null) {
            return null;
        } else {
            return new Timestamp(dateTime.getMillis());
        }
    }

    @Override
    public Class<Timestamp> fromType() {
        return Timestamp.class;
    }

    @Override
    public Class<DateTime> toType() {
        return DateTime.class;
    }
}
