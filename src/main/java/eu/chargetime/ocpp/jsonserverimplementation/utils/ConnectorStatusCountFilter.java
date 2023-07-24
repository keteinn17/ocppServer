package eu.chargetime.ocpp.jsonserverimplementation.utils;


import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.ConnectorStatus;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * @author ket_ein17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectorStatusCountFilter {
    public static final Set<String> ALL_STATUS_VALUES = allStatusValues();

    public static Map<String, Integer> getStatusCountMap(List<ConnectorStatus> latestList) {
        return getStatusCountMap(latestList, false);
    }

    public static Map<String, Integer> getStatusCountMap(List<ConnectorStatus> latestList, boolean printZero) {
        List<ConnectorStatus> filteredList = ConnectorStatusFilter.filterAndPreferZero(latestList);

        // TreeMap because we want a consistent order of the listing on the page
        TreeMap<String, Integer> map = new TreeMap<>();
        for (ConnectorStatus item : filteredList) {
            Integer count = map.get(item.getStatus());
            if (count == null) {
                count = 1;
            } else {
                count += 1;
            }
            map.put(item.getStatus(), count);
        }

        if (printZero) {
            ALL_STATUS_VALUES.forEach(s -> map.putIfAbsent(s, 0));
        }

        return map;
    }

    private static Set<String> allStatusValues() {
        // to have a predictable sorting on the web page
        TreeSet<String> set = new TreeSet<>(Comparator.naturalOrder());

//        EnumSet.allOf(ocpp.cs._2010._08.ChargePointStatus.class).forEach(k -> set.add(k.value()));
//        EnumSet.allOf(ocpp.cs._2012._06.ChargePointStatus.class).forEach(k -> set.add(k.value()));
        EnumSet.allOf(ChargePointStatus.class).forEach(k -> set.add(k.toString()));

        return set;
    }
}
