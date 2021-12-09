package net.erchen.adventofcode2021.day09;

import net.erchen.adventofcode2021.common.Matrix;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Comparator.reverseOrder;

public class SmokeBasinDetector {

    private final Matrix<Integer> heights;

    public SmokeBasinDetector(String input) {
        this.heights = Matrix.fromInput(input, "\n", "", Integer::parseInt);
    }

    public int calculateRiskLevel() {
        return findLowPoints()
            .mapToInt(field -> field.getValue() + 1)
            .sum();
    }

    public long largestBasinsSize() {
        return findLowPoints()
            .map(field -> measureBasinSize(field, -1, new LinkedList<>()))
            .sorted(reverseOrder())
            .limit(3)
            .mapToLong(Long::valueOf)
            .reduce(1, (l1, l2) -> l1 * l2);
    }

    int measureBasinSize(Matrix<Integer>.Field field, Integer callerValue, List<Integer> basinFields) {
        if (basinFields.contains(field.hashCode())) {
            return 0;
        }

        if (field.getValue() > callerValue && field.getValue() != 9) {
            basinFields.add(field.hashCode());
            return 1 + field.getAdjacents().mapToInt(adjacent -> measureBasinSize(adjacent, field.getValue(), basinFields)).sum();
        }

        return 0;
    }

    private Stream<Matrix<Integer>.Field> findLowPoints() {
        return heights.allFields()
            .filter(field -> field.getAdjacents().allMatch(adjacent -> adjacent.getValue() > field.getValue()));
    }
}
