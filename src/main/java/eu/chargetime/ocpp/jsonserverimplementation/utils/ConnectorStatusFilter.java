package eu.chargetime.ocpp.jsonserverimplementation.utils;

import eu.chargetime.ocpp.jsonserverimplementation.repository.dto.ConnectorStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author ket_ein17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectorStatusFilter {
    public static List<ConnectorStatus> filterAndPreferZero(List<ConnectorStatus> initialList) {
        return processAndFilterList(initialList, Strategy.PreferZero);
    }

    private static List<ConnectorStatus> processAndFilterList(List<ConnectorStatus> initialList, Strategy strategy) {
        return initialList.stream()
                .collect(Collectors.groupingBy(ConnectorStatus::getChargeBoxId))
                .values()
                .stream()
                .flatMap(val -> processForOneStation(val, strategy).stream())
                .collect(Collectors.toList());
    }

    private enum Strategy implements ZeroMoreRecentStrategy {

        PreferZero {
            @Override
            public List<ConnectorStatus> process(List<ConnectorStatus> zero, List<ConnectorStatus> nonZero) {
                return zero;
            }
        },

        /**
         * If connector 0 is more recent, copy the status of connector 0 to
         * other connector ids, and return ONLY others.
         */
        PreferOthersWithStatusOfZero {
            @Override
            public List<ConnectorStatus> process(List<ConnectorStatus> zero, List<ConnectorStatus> nonZero) {

                ConnectorStatus zeroStat = zero.get(0); // we are sure that there is only one

                return nonZero.stream()
                        .map(cs -> ConnectorStatus.builder()
                                .chargeBoxPk(cs.getChargeBoxPk())
                                .chargeBoxId(cs.getChargeBoxId())
                                .connectorId(cs.getConnectorId())
                                .timeStamp(zeroStat.getTimeStamp())
                                .statusTimestamp(zeroStat.getStatusTimestamp())
                                .status(zeroStat.getStatus())
                                .errorCode(zeroStat.getErrorCode())
                                .ocppProtocol(cs.getOcppProtocol())
                                .jsonAndDisconnected(cs.isJsonAndDisconnected())
                                .build())
                        .collect(Collectors.toList());
            }
        }
    }

    private static List<ConnectorStatus> processForOneStation(List<ConnectorStatus> statsList, Strategy strategy) {
        Map<Boolean, List<ConnectorStatus>> partition =
                statsList.stream()
                        .collect(Collectors.partitioningBy(s -> s.getConnectorId() == 0));

        List<ConnectorStatus> zero = partition.get(Boolean.TRUE);
        List<ConnectorStatus> nonZero = partition.get(Boolean.FALSE);

        Optional<ConnectorStatus> maxZero =
                zero.stream()
                        .max(Comparator.comparing(ConnectorStatus::getStatusTimestamp));

        Optional<ConnectorStatus> maxNonZero =
                nonZero.stream()
                        .max(Comparator.comparing(ConnectorStatus::getStatusTimestamp));

        // decide what to return
        //
        if (maxZero.isPresent()) {
            Predicate<ConnectorStatus> pr = o -> o.getStatusTimestamp().isAfter(maxZero.get().getStatusTimestamp());

            if (maxNonZero.filter(pr).isPresent()) {
                return nonZero;
            } else {
                // this is the special case we need to handle
                return strategy.process(zero, nonZero);
            }
        } else if (maxNonZero.isPresent()) {
            return nonZero;

        } else {
            return Collections.emptyList();
        }
    }

    private interface ZeroMoreRecentStrategy {
        List<ConnectorStatus> process(List<ConnectorStatus> zero, List<ConnectorStatus> nonZero);
    }
}
