package net.erchen.adventofcode2021.day11;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import net.erchen.adventofcode2021.common.Matrix;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class DumboOctopus {

    private final Matrix<AtomicInteger> octopuses;
    private int flashes;

    public static DumboOctopus fromInput(String input) {
        return DumboOctopus.builder().octopuses(Matrix.fromInput(input, "\n", "", s -> new AtomicInteger(Integer.parseInt(s)))).build();
    }

    public void simulateSteps(int steps) {
        for (int i = 0; i < steps; i++) {
            Set<Integer> flashed = new HashSet<>();
            octopuses.allFields().map(Matrix.Field::getValue).forEach(AtomicInteger::incrementAndGet);
            octopuses.allFields().filter(f -> f.getValue().intValue() > 9).forEach(f -> flash(f, flashed));
            octopuses.allFields().filter(f -> f.getValue().intValue() > 9).forEach(f -> f.getValue().set(0));
            flashes += flashed.size();
        }
    }

    private void flash(Matrix<AtomicInteger>.Field field, Set<Integer> flashed) {
        if (!flashed.contains(field.hashCode())) {
            flashed.add(field.hashCode());
            field.getAdjacentsWithDiagonals().forEach(adjacent -> {
                var newValue = adjacent.getValue().incrementAndGet();
                if (newValue == 10) {
                    flash(adjacent, flashed);
                }
            });
        }
    }

    public int simulateUntilAllFlashes() {
        int step = 0;
        while (!octopuses.allFields().allMatch(i -> i.getValue().intValue() == 0)) {
            simulateSteps(1);
            step++;
        }
        return step;
    }
}
