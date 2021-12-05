package net.erchen.adventofcode2021.day05;

import net.erchen.adventofcode2021.common.Matrix;

import java.util.concurrent.atomic.AtomicInteger;

public class OceanFloor {

    private final Matrix<AtomicInteger> hydrothermalVents;

    public OceanFloor(int dimension) {
        this.hydrothermalVents = Matrix.fromInitValue(dimension, () -> new AtomicInteger(0));
    }

    public void applyLine(int x1, int y1, int x2, int y2, boolean ignoreDiagonal) {
        var isDiagonal = x1 != x2 && y1 != y2;
        if (ignoreDiagonal && isDiagonal) {
            return;
        }
        int x = x1;
        int y = y1;
        while (x != x2 || y != y2) {
            hydrothermalVents.field(x, y).incrementAndGet();
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
        hydrothermalVents.field(x2, y2).incrementAndGet();
    }

    public long countDangerousAreas() {
        return hydrothermalVents.allFields().filter(i -> i.intValue() > 1).count();
    }

    public String toString() {
        return hydrothermalVents.toString();
    }

}
