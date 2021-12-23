package net.erchen.adventofcode2021.day23;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static net.erchen.adventofcode2021.day23.AmphipodBurrow.State.emptyState;

@Slf4j
@Getter
public class AmphipodBurrow {

    private final Map<Integer, Field> burrow;
    private final Amphipod[] positions;
    private final int roomSize;

    // debugging access
    private final List<Field> hallway;
    private final List<Field> homeA;
    private final List<Field> homeB;
    private final List<Field> homeC;
    private final List<Field> homeD;

    public AmphipodBurrow(Amphipod... initialPositions) {
        roomSize = initialPositions.length / 4;
        AtomicInteger positionReference = new AtomicInteger(0);
        hallway = generateConnectedFields(11, (i) -> Field.builder().debuggingName("Hallyway " + i).positionReference(positionReference.getAndIncrement()).build());
        homeA = generateConnectedFields(roomSize, (i) -> Field.builder().debuggingName("Home A " + i).homeFor(Amphipod.Amber).positionReference(positionReference.getAndIncrement()).build());
        homeB = generateConnectedFields(roomSize, (i) -> Field.builder().debuggingName("Home B " + i).homeFor(Amphipod.Bronze).positionReference(positionReference.getAndIncrement()).build());
        homeC = generateConnectedFields(roomSize, (i) -> Field.builder().debuggingName("Home C " + i).homeFor(Amphipod.Copper).positionReference(positionReference.getAndIncrement()).build());
        homeD = generateConnectedFields(roomSize, (i) -> Field.builder().debuggingName("Home D " + i).homeFor(Amphipod.Desert).positionReference(positionReference.getAndIncrement()).build());

        connectFields(homeA.get(0), hallway.get(2));
        connectFields(homeB.get(0), hallway.get(4));
        connectFields(homeC.get(0), hallway.get(6));
        connectFields(homeD.get(0), hallway.get(8));

        positions = new Amphipod[positionReference.intValue()];

        int i = 0;
        for (int home = 0; home < roomSize; home++) {
            positions[homeA.get(home).positionReference] = initialPositions[i++];
            positions[homeB.get(home).positionReference] = initialPositions[i++];
            positions[homeC.get(home).positionReference] = initialPositions[i++];
            positions[homeD.get(home).positionReference] = initialPositions[i++];
        }

        this.burrow = Stream.of(hallway, homeA, homeB, homeC, homeD).flatMap(Collection::stream).collect(toMap(f -> f.positionReference, identity()));
    }

    private void connectFields(Field a, Field b) {
        a.adjacents.add(b);
        b.adjacents.add(a);
    }

    private static List<Field> generateConnectedFields(int count, Function<Integer, Field> fieldCreator) {
        var fields = IntStream.range(0, count).mapToObj(fieldCreator::apply).toList();
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) {
                fields.get(i).adjacents.add(fields.get(i - 1));
            }
            if (i < fields.size() - 1) {
                fields.get(i).adjacents.add(fields.get(i + 1));
                fields.get(i).roomBelow.addAll(IntStream.range(i + 1, count).mapToObj(fields::get).toList());
            }
        }
        return fields;
    }

    public long findLeastTotalEnergyWay() {
        var todo = new PriorityQueue<>(1, Comparator.comparingLong(State::score));
        var done = new HashMap<State, Long>();

        todo.add(emptyState(positions));
        int logI = 0;

        while (todo.size() > 0) {
            var current = todo.poll();
            if (logI++ % 1000 == 0) {
                log.debug("todo: {}, done: {}, current score: {}", todo.size(), done.size(), current.score());
            }

            for (State state : nextStates(current)) {
                if (done.containsKey(state) && done.get(state) <= state.totalEnergy) {
                    continue;
                }
                done.put(state, state.totalEnergy);
                if (state.estimatedEnergy == 0) {
                    log.info("Found min with score {}: {}", state.totalEnergy, state);
                    return state.totalEnergy;
                } else {
                    todo.add(state);
                }
            }
        }
        return -1;
    }

    List<State> nextStates(State state) {
        return IntStream.range(0, positions.length)
                .filter(position -> position != state.lastPositionMoved)
                .filter(position -> !state.isFree(position))
                .boxed()
                .flatMap(position -> nextFields(burrow.get(position), burrow.get(position), state.positions[position], null, 0, state).map(newField -> state.move(position, newField, this::estimateEnergyConsumption)))
                .toList();
    }

    Stream<FieldWithCosts> nextFields(Field field, Field origin, Amphipod type, Field previous, int fieldCount, State state) {
        if (field.homeFor == type && field.allBelow().allMatch(below -> positions[below.positionReference] == type)) {
            return Stream.of();
        }
        return field.adjacents.stream()
                .filter(adjacent -> !adjacent.equals(previous))
                .filter(state::isFree)
                .flatMap(adjacent -> Stream.concat(Stream.of(new FieldWithCosts(adjacent, fieldCount + 1)), nextFields(adjacent, origin, type, field, fieldCount + 1, state)))
                .filter(nextField -> nextField.field().isStopAllowed(type, origin, state));
    }

    private long estimateEnergyConsumption(Amphipod[] positions) {
        return IntStream.range(0, positions.length)
                .filter(position -> positions[position] != null)
                .map(position -> {
                    var amphipod = positions[position];
                    var field = burrow.get(position);

                    if (field.homeFor == amphipod) {
                        if (field.allBelow().allMatch(below -> positions[below.positionReference] == amphipod)) { // is final position
                            return 0;
                        } else {
                            return (roomSize - (int) field.allBelow().count() + roomSize) * 2 * amphipod.energyConsumption;
                        }
                    }

                    var estimatedFieldCount = 0;
                    if (field.homeFor != null) {
                        estimatedFieldCount += Math.abs(field.homeFor.position - amphipod.position) + (roomSize - field.allBelow().count()) + 1;
                    } else {
                        estimatedFieldCount += Math.abs(position - amphipod.position) + 1;
                    }

                    return amphipod.energyConsumption * estimatedFieldCount;
                }).sum();
    }

    public static record State(Amphipod[] positions, State last, long totalEnergy, long estimatedEnergy, int steps, int lastPositionMoved) {

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            State state = (State) o;
            return Arrays.equals(positions, state.positions);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(positions);
        }

        public String toString() {
            if (positions.length < 26) {
                return Arrays.toString(positions);
            }
            var chars = Arrays.stream(positions).map(amphipod -> amphipod == null ? " " : amphipod.name().substring(0, 1)).toArray(String[]::new);
            return (last != null ? last.toString() : "") + """
                                        
                    #############
                    #§00§01§02§03§04§05§06§07§08§09§10#  §totalEnergy
                    ###§11#§15#§19#§23###
                      #§12#§16#§20#§24#
                      #§13#§17#§21#§25#
                      #§14#§18#§22#§26#
                      #########
                    """
                    .replace("§00", chars[0])
                    .replace("§01", chars[1])
                    .replace("§02", chars[2])
                    .replace("§03", chars[3])
                    .replace("§04", chars[4])
                    .replace("§05", chars[5])
                    .replace("§06", chars[6])
                    .replace("§07", chars[7])
                    .replace("§08", chars[8])
                    .replace("§09", chars[9])
                    .replace("§10", chars[10])
                    .replace("§11", chars[11])
                    .replace("§12", chars[12])
                    .replace("§13", chars[13])
                    .replace("§14", chars[14])
                    .replace("§15", chars[15])
                    .replace("§16", chars[16])
                    .replace("§17", chars[17])
                    .replace("§18", chars[18])
                    .replace("§19", chars[19])
                    .replace("§20", chars[20])
                    .replace("§21", chars[21])
                    .replace("§22", chars[22])
                    .replace("§23", chars[23])
                    .replace("§24", chars[24])
                    .replace("§25", chars[25])
                    .replace("§26", chars[26])
                    .replace("§totalEnergy", String.format("%6d", totalEnergy));
        }

        public static State emptyState(Amphipod[] positions) {
            return new State(positions, null, 0, 0, 0, -1);
        }

        public long score() {
            return totalEnergy + estimatedEnergy;
        }

        public boolean isFree(int positionReference) {
            return positions[positionReference] == null;
        }

        public boolean isFree(Field field) {
            return isFree(field.positionReference);
        }

        public State move(int from, FieldWithCosts to, Function<Amphipod[], Long> estimatedEnergyCostsCalculator) {
            var newPositions = Arrays.copyOf(positions, positions.length);
            newPositions[to.field.positionReference] = positions[from];
            newPositions[from] = null;
            return new State(newPositions, this, totalEnergy + (long) to.costs * positions[from].energyConsumption, estimatedEnergyCostsCalculator.apply(newPositions), steps + 1, to.field().positionReference);
        }

    }

    public record FieldWithCosts(Field field, int costs) {

    }

    @Builder
    @Getter
    public static class Field {
        @Builder.Default
        private final List<Field> adjacents = new LinkedList<>();
        @Builder.Default
        private final List<Field> roomBelow = new LinkedList<>();
        private final Amphipod homeFor;
        private final String debuggingName;
        private final int positionReference;

        public boolean isStopAllowed(Amphipod type, Field from, State state) {
            return adjacents.size() < 3 && ((homeFor == null && from.homeFor != null) || (homeFor == type && allBelow().allMatch(below -> state.positions[below.positionReference] == type)));
        }

        public Stream<Field> allBelow() {
            if (homeFor == null) {
                return Stream.empty();
            }
            return roomBelow.stream();
        }

        @Override
        public String toString() {
            return debuggingName;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum Amphipod {
        Amber(1, 2), Bronze(10, 4), Copper(100, 6), Desert(1000, 8);

        private final int energyConsumption;
        private final int position;
    }
}
