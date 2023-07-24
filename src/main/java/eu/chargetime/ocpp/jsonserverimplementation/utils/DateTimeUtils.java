package eu.chargetime.ocpp.jsonserverimplementation.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.jooq.DSLContext;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtils {
    private static final DateTimeFormatter HUMAN_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd 'at' HH:mm");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat.forPattern("HH:mm");

    private static final PeriodFormatter PERIOD_FORMATTER = new PeriodFormatterBuilder()
            .printZeroNever()
            .appendDays().appendSuffix(" day", " days").appendSeparator(" ")
            .appendHours().appendSuffix(" hour", " hours").appendSeparator(" ")
            .appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(" ")
            .appendSeconds().appendSuffix(" second", " seconds")
            .toFormatter();

    public static DateTime toDateTime(LocalDateTime ldt) {
        if (ldt == null) {
            return null;
        } else {
            return ldt.toDateTime();
        }
    }

    public static LocalDateTime toLocalDateTime(DateTime dt) {
        if (dt == null) {
            return null;
        } else {
            return dt.toLocalDateTime();
        }
    }

    /**
     * Print the date/time nicer, if it's from today, yesterday or tomorrow.
     */
    public static String humanize(DateTime dt) {
        if (dt == null) {
            return "";
        }

        // Equalize time fields before comparing date fields
        DateTime inputAtMidnight = dt.withTimeAtStartOfDay();
        DateTime todayAtMidnight = DateTime.now().withTimeAtStartOfDay();

        // Is it today?
        if (inputAtMidnight.equals(todayAtMidnight)) {
            return "Today at " + TIME_FORMATTER.print(dt);

            // Is it yesterday?
        } else if (inputAtMidnight.equals(todayAtMidnight.minusDays(1))) {
            return "Yesterday at " + TIME_FORMATTER.print(dt);

            // Is it tomorrow?
        } else if (inputAtMidnight.equals(todayAtMidnight.plusDays(1))) {
            return "Tomorrow at " + TIME_FORMATTER.print(dt);

            // So long ago OR in the future...
        } else {
            return HUMAN_FORMATTER.print(dt);
        }
    }

    public static String timeElapsed(DateTime from, DateTime to) {
        return PERIOD_FORMATTER.print(new Period(from, to));
    }

    public static void checkJavaAndMySQLOffsets(DSLContext ctx) throws Exception {
        long sql = CustomDSL.selectOffsetFromUtcInSeconds(ctx);
        long java = DateTimeUtils.getOffsetFromUtcInSeconds();

        if (sql != java) {
            throw new Exception("MySQL and Java are not using the same time zone. " +
                    "Java offset in seconds (%s) != MySQL offset in seconds (%s)");
        }
    }

    private static long getOffsetFromUtcInSeconds() {
        DateTimeZone timeZone = DateTimeZone.getDefault();
        DateTime now = DateTime.now();
        long offsetInMilliseconds = timeZone.getOffset(now.getMillis());
        return TimeUnit.MILLISECONDS.toSeconds(offsetInMilliseconds);
    }
    public static ZonedDateTime jodaToZoned(DateTime dt){
        if(!Objects.isNull(dt)){
            Instant instant = Instant.ofEpochMilli(dt.getMillis());
            ZoneId zoneId = ZoneId.of(dt.getZone().getID(), ZoneId.SHORT_IDS);
            ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, zoneId);
            return zdt;
        }
        else return null;
    }

    public static java.time.LocalDateTime toLocalDateTime(ZonedDateTime source) {
        if(!Objects.isNull(source)){
            return source.toLocalDateTime();
        }else return null;
    }
}
