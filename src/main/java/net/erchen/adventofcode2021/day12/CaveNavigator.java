package net.erchen.adventofcode2021.day12;

import java.util.*;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class CaveNavigator {

    private static final String DELIMITER = "-";
    private final List<Set<String>> connections;
    private final boolean part2;

    public CaveNavigator(List<String> inputs, boolean part2) {
        this.connections = inputs.stream()
            .map(line -> Stream.of(line.split(DELIMITER)).collect(toSet()))
            .toList();
        this.part2 = part2;
    }

    public List<String> calculatePaths() {
        List<String> allRoutes = new LinkedList<>();

        allRoutes.add("start");

        for (var routeCount = allRoutes.size(); ; routeCount = allRoutes.size()) {
            allRoutes = allRoutes.stream().flatMap(this::nextConnections).distinct().toList();
            if (allRoutes.size() == routeCount) {
                break;
            }
        }
        return allRoutes;
    }

    private boolean isAllowed(String connection, String next) {
        if (next.equals("start")) {
            return false;
        }
        if (next.toLowerCase().equals(next)) {
            if (part2) {
                return isAllowedPath(connection + DELIMITER + next);
            } else {
                return !connection.contains(next);
            }
        }
        return true;
    }

    static boolean isAllowedPath(String connection) {
        var countPerChar = Arrays.stream(connection.replace("start-", "").replace("-end", "").split(DELIMITER))
            .filter(c -> c.equals(c.toLowerCase()))
            .collect(groupingBy(identity(), counting()));

        return countPerChar.values().stream().filter(l -> l > 1).count() <= 1 && countPerChar.values().stream().noneMatch(l -> l > 2);
    }

    private String last(String connection) {
        return connection.substring(Math.max(0, connection.lastIndexOf('-') + 1));
    }

    Stream<String> next(String start) {
        return connections.stream()
            .filter(connection -> connection.contains(start))
            .flatMap(Collection::stream)
            .filter(item -> !item.equals(start));
    }

    private Stream<String> nextConnections(String connection) {
        var last = last(connection);
        if (last.equals("end")) {
            return Stream.of(connection);
        }
        return next(last).filter(next -> isAllowed(connection, next)).map(next -> connection + DELIMITER + next);
    }


}
