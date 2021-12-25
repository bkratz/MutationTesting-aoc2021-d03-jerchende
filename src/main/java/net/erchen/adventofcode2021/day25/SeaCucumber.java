package net.erchen.adventofcode2021.day25;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.erchen.adventofcode2021.common.Matrix;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Getter
public class SeaCucumber {

    private final Matrix<Field> ocean;

    public SeaCucumber(String input) {
        ocean = Matrix.fromInput(input, "\n", "", Field::fromRepresentation);
    }

    public int moveUntilNoMoreMoves() {
        int steps = 1;
        while (move()) {
            steps++;
        }
        return steps;
    }

    public boolean move() {
        var moveEast = move(Field.EAST, Matrix.Field::rightWithOverflow);
        var moveSouth = move(Field.SOUTH, Matrix.Field::bottomWithOverflow);
        return moveEast || moveSouth;
    }

    private boolean move(Field type, Function<Matrix<Field>.Field, Matrix<Field>.Field> nextField) {
        var swap = ocean.allFields().filter(field -> field.getValue() == type).flatMap(field -> {
            var right = nextField.apply(field);
            if (right.getValue() == Field.EMPTY) {
                return Stream.of(Map.entry(field, right));
            }
            return Stream.of();
        }).toList();
        if (swap.isEmpty()) {
            return false;
        }
        swap.forEach(x -> ocean.swapValues(x.getKey(), x.getValue()));
        return true;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Field {
        EAST(">"), SOUTH("v"), EMPTY(".");

        private final String representation;

        @Override public String toString() {
            return getRepresentation();
        }

        public static Field fromRepresentation(String s) {
            return Arrays.stream(Field.values()).filter(f -> f.representation.equals(s)).findAny().orElseThrow();
        }
    }
}
