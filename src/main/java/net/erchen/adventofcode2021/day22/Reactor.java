package net.erchen.adventofcode2021.day22;

import lombok.Builder;
import lombok.With;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.Collections.emptyList;

@Slf4j
public class Reactor {

    private final static Pattern CUBE_PATTERN = Pattern.compile("(on|off) x=(-?[0-9]+)\\.\\.(-?[0-9]+),y=(-?[0-9]+)\\.\\.(-?[0-9]+),z=(-?[0-9]+)\\.\\.(-?[0-9]+)");

    private List<Cuboid> cuboids = emptyList();

    public void rebootInstruction(List<String> input) {
        input.forEach(this::rebootInstruction);
    }

    public void rebootInstruction(String input) {
        var cuboid = Cuboid.fromInput(input);
        if (cuboids.isEmpty()) {
            if (cuboid.isOn()) {
                cuboids = List.of(cuboid);
            }
            return;
        }
        if (cuboids.stream().anyMatch(existing -> existing.fullyContains(cuboid) && existing.isOn() == cuboid.isOn())) {
            log.debug("No changes");
        }
        cuboids = Stream.concat(
                        cuboids.stream()
                                .flatMap(existing -> existing.split(cuboid))
                                .filter(existing -> !cuboid.fullyContains(existing))
                        ,
                        cuboid.isOn() ? Stream.of(cuboid) : Stream.of())
                .toList();
        log.debug("Currently {} cuboids active.", cuboids.size());
    }

    public long countActiveCubes() {
        return cuboids.stream().mapToLong(Cuboid::volume).sum();
    }

    /**
     * x1,y1,z1 is inclusive
     * x2,y2,z2 is exclusive
     */
    @With
    public static record Cuboid(int x1, int x2, int y1, int y2, int z1, int z2, boolean state) {

        @Builder
        public Cuboid {
        }

        public static Cuboid fromInput(String input) {
            var matcher = CUBE_PATTERN.matcher(input);
            if (!matcher.find()) {
                throw new IllegalArgumentException("Invalid cube: " + input);
            }

            return Cuboid.builder()
                    .x1(parseInt(matcher.group(2)))
                    .x2(parseInt(matcher.group(3)) + 1)
                    .y1(parseInt(matcher.group(4)))
                    .y2(parseInt(matcher.group(5)) + 1)
                    .z1(parseInt(matcher.group(6)))
                    .z2(parseInt(matcher.group(7)) + 1)
                    .state(matcher.group(1).equals("on"))
                    .build();
        }

        public boolean isOn() {
            return state;
        }

        public int width() {
            return x2 - x1;
        }

        public int height() {
            return y2 - y1;
        }

        public int depth() {
            return z2 - z1;
        }

        public long volume() {
            return (long) width() * height() * depth();
        }

        @Override
        public String toString() {
            return (state ? "on" : "off") + " "
                    + "x=" + x1 + ".." + (x2 - 1) + ","
                    + "y" + x1 + ".." + (y2 - 1) + ","
                    + "z=" + z1 + ".." + (z2 - 1);
        }

        public boolean fullyContains(Cuboid other) {
            return other.x1 >= x1 &&
                    other.x2 <= x2 &&
                    other.y1 >= y1 &&
                    other.y2 <= y2 &&
                    other.z1 >= z1 &&
                    other.z2 <= z2;
        }

        public boolean overLaps(Cuboid other) {
            return (other.x1 >= x1 && other.x2 <= x2) ||
                    (other.y1 >= y1 && other.y2 <= y2) ||
                    (other.z1 >= z1 && other.z2 <= z2);
        }

        // TODO do not split in so many parts to increase performance
        public Stream<Cuboid> split(Cuboid splitter) {
            return splitX(splitter.x1)
                    .flatMap(part -> part.splitX(splitter.x2))
                    .flatMap(part -> part.splitY(splitter.y1))
                    .flatMap(part -> part.splitY(splitter.y2))
                    .flatMap(part -> part.splitZ(splitter.z1))
                    .flatMap(part -> part.splitZ(splitter.z2));
        }

        private Stream<Cuboid> splitX(int xAxis) {
            if (xAxis > x1 && xAxis < x2) {
                return Stream.of(this.withX2(xAxis), this.withX1(xAxis));
            } else {
                return Stream.of(this);
            }
        }

        private Stream<Cuboid> splitY(int yAxis) {
            if (yAxis > y1 && yAxis < y2) {
                return Stream.of(this.withY2(yAxis), this.withY1(yAxis));
            } else {
                return Stream.of(this);
            }
        }

        private Stream<Cuboid> splitZ(int zAxis) {
            if (zAxis > z1 && zAxis < z2) {
                return Stream.of(this.withZ2(zAxis), this.withZ1(zAxis));
            } else {
                return Stream.of(this);
            }
        }
    }
}
