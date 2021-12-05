package net.erchen.adventofcode2021.day05;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class OceanFloor {

    private final int[][] hydrothermalVents;

    public OceanFloor(int dimension) {
        this.hydrothermalVents = new int[dimension][dimension];
    }

    public void applyLine(int x1, int y1, int x2, int y2, boolean ignoreDiagonal) {
        var isDiagonal = x1 != x2 && y1 != y2;
        if (ignoreDiagonal && isDiagonal) {
            return;
        }
        int x = x1;
        int y = y1;
        while (x != x2 || y != y2) {
            hydrothermalVents[y][x]++;
            if (x < x2) {
                x++;
            } else if (x > x2) {
                x--;
            }
            if (y < y2) {
                y++;
            } else if (y > y2) {
                y--;
            }
        }
        hydrothermalVents[y2][x2]++;
    }

    public long countDangerousAreas() {
        return Arrays.stream(hydrothermalVents).flatMapToInt(Arrays::stream).filter(i -> i > 1).count();
    }

    public String toString() {
        return Arrays.stream(hydrothermalVents)
            .map(line -> Arrays.stream(line)
                .mapToObj(String::valueOf)
                .collect(joining(" ")))
            .collect(joining("\n"));
    }

}
