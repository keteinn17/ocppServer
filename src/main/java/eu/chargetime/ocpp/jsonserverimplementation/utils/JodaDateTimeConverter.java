package eu.chargetime.ocpp.jsonserverimplementation.utils;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author ket_ein17
 */
public class JodaDateTimeConverter extends XmlAdapter<String, DateTime> {
    public JodaDateTimeConverter() {
    }

    public DateTime unmarshal(String v) throws Exception {
        return isNullOrEmpty(v) ? null : new DateTime(v);
    }

    public String marshal(DateTime v) throws Exception {
        return v == null ? null : v.toString();
    }

    private static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
