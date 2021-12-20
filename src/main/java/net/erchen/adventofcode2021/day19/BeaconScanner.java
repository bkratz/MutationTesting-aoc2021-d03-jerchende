package net.erchen.adventofcode2021.day19;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class BeaconScanner {

    private List<Scanner> scanners;
    private int overlappingCount;
    @Builder.Default
    private Set<Point> map = new HashSet<>();

    public static BeaconScanner fromInput(String input, int overlappingCount) {
        return BeaconScanner.builder()
                .scanners(Arrays.stream(input.split("\n\n")).map(Scanner::fromInput).toList())
                .overlappingCount(overlappingCount)
                .build();

    }

    public void calculateMap() {
        var solved = new LinkedList<Scanner>();
        var base = scanners.get(0);
        addToMap(base, 0, 0, 0);

        solved.add(base);

        while (solved.size() < scanners.size()) {
            for (Scanner unlocatedScanner : scanners) {
                if (solved.contains(unlocatedScanner)) {
                    continue;
                }
                unlocatedScanner.allOrientations().map(this::overlapsWithMap).flatMap(Optional::stream).findFirst().ifPresent(offset -> {
                    addToMap(unlocatedScanner, offset.getX(), offset.getY(), offset.getZ());
                    solved.add(unlocatedScanner);
                });
            }
        }
    }

    public int calculateOceanSize() {
        return scanners.stream().flatMap(scanner1 -> scanners.stream().map(scanner2 -> getDistance(scanner1.getPosition(), scanner2.getPosition()))).mapToInt(Integer::valueOf).max().orElse(0);
    }

    private Optional<Point> overlapsWithMap(Scanner scanner) {
        return map.stream().flatMap(mapPoint -> scanner.getPoints().stream().map(scannerPoint -> subtractPoint(mapPoint, scannerPoint)))
                .collect(groupingBy(identity(), counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= overlappingCount)
                .findFirst()
                .map(Map.Entry::getKey);
    }

    private void addToMap(Scanner scanner, int offsetX, int offsetY, int offsetZ) {
        scanner.setPosition(Point.builder().x(offsetX).y(offsetY).z(offsetZ).build());
        map.addAll(scanner.getPoints().stream().map(point -> Point.builder().x(point.getX() + offsetX).y(point.getY() + offsetY).z(point.getZ() + offsetZ).build()).toList());
    }

    private static Point subtractPoint(Point a, Point b) {
        return Point.builder().x(a.getX() - b.getX()).y(a.getY() - b.getY()).z(a.getZ() - b.getZ()).build();
    }

    private int getDistance(Point a, Point b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()) + Math.abs(a.getZ() - b.getZ());
    }

    @Data
    @Builder
    static class Scanner {
        private String name;
        private List<Point> points;
        private Point position;

        public static Scanner fromInput(String input) {
            var lines = input.split("\n");

            return Scanner.builder()
                    .name(lines[0])
                    .points(Arrays.stream(lines).skip(1).map(Point::fromInput).toList())
                    .build();
        }

        public Scanner roll() {
            this.points.forEach(Point::roll);
            return this;
        }

        public Scanner turn() {
            this.points.forEach(Point::turn);
            return this;
        }

        public Scanner reverseTurn() {
            this.points.forEach(Point::reverseTurn);
            return this;
        }

        public Stream<Scanner> allOrientations() {
            return IntStream.range(0, 6).boxed().flatMap(rollIndex -> Stream.concat(Stream.of(this.roll()), IntStream.range(0, 3).boxed().map(turnIndex -> rollIndex % 2 == 0 ? this.turn() : this.reverseTurn())));
        }
    }

    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    static class Point {
        private int x;
        private int y;
        private int z;

        public static Point fromInput(String input) {
            var inputs = input.split(",");
            return Point.builder()
                    .x(parseInt(inputs[0]))
                    .y(parseInt(inputs[1]))
                    .z(parseInt(inputs[2]))
                    .build();
        }

        public Point roll() {
            var oldY = y;

            y = z;
            z = -oldY;
            return this;
        }

        public Point turn() {
            var oldX = x;
            x = -y;
            y = oldX;
            return this;
        }

        public Point reverseTurn() {
            var oldX = x;
            x = y;
            y = -oldX;
            return this;
        }
    }
}
