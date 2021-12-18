package net.erchen.adventofcode2021.day18;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.function.Consumer;

public class SnailfishMath {

    public static SnailfishNumber sumNumbers(List<String> input) {
        return input.stream().map(SnailfishMath::parseNumber).reduce(null, (a, b) -> a == null ? b : add(a, b));
    }

    public static SnailfishNumber parseNumber(String input) {
        return parseNumber(input, null);
    }

    public static SnailfishNumber add(SnailfishNumber a, SnailfishNumber b) {
        SnailfishNumberPair pair = new SnailfishNumberPair();
        pair.setLeft(a);
        pair.setRight(b);
        a.setParent(pair);
        b.setParent(pair);
        return reduce(pair);
    }

    private static SnailfishNumber parseNumber(String input, SnailfishNumberPair parent) {
        if (input.matches("[0-9]+")) {
            return new SnailfishRegularNumber(Integer.parseInt(input));
        }

        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
            case '[' -> depth++;
            case ']' -> depth--;
            case ',' -> {
                if (depth == 1) {
                    var pair = new SnailfishNumberPair();
                    pair.setParent(parent);
                    pair.setLeft(parseNumber(input.substring(1, i), pair));
                    pair.setRight(parseNumber(input.substring(i + 1, input.length() - 1), pair));
                    return pair;
                }
            }
            }
        }
        throw new IllegalStateException("Input is not a valid snailfish number!");
    }

    public static SnailfishNumber reduce(SnailfishNumber number) {
        final Consumer<SnailfishRegularNumber> emptyRegularNumberConsumer = x -> {
        };
        final Consumer<SnailfishNumberPair> emptyPairConsumer = x -> {
        };

        while (number.explode(0, emptyRegularNumberConsumer) || number.split(emptyPairConsumer)) {
            // empty
        }
        return number;
    }

    public static int findHighestMagnitude(List<String> inputs) {
        var numbers = inputs.stream().map(SnailfishMath::parseNumber).toList();
        return numbers.stream()
                .flatMap(first -> numbers.stream()
                        .filter(second -> first != second)
                        .map(second -> add(first.clone(), second.clone())))
                .mapToInt(SnailfishNumber::magnitude)
                .max().orElseThrow();
    }

    public interface SnailfishNumber {
        boolean explode(int depth, Consumer<SnailfishRegularNumber> parentSetter);

        boolean split(Consumer<SnailfishNumberPair> parentSetter);

        void setParent(SnailfishNumberPair parent);

        int magnitude();

        SnailfishNumber clone();

    }

    @Data
    public static class SnailfishNumberPair implements SnailfishNumber {
        private SnailfishNumberPair parent;
        private SnailfishNumber left;
        private SnailfishNumber right;

        public String toString() {
            return "[" + left + "," + right + "]";
        }

        public boolean explode(int depth, Consumer<SnailfishRegularNumber> parentSetter) {
            if (depth == 4) {
                var leftAdjacent = leftAdjacent();
                var rightAdjacent = rightAdjacent();

                if (leftAdjacent != null) {
                    leftAdjacent.add(((SnailfishRegularNumber) left).getValue());
                }
                if (rightAdjacent != null) {
                    rightAdjacent.add(((SnailfishRegularNumber) right).getValue());
                }
                parentSetter.accept(new SnailfishRegularNumber(0));
                return true;
            }
            return this.left.explode(depth + 1, (x) -> this.left = x) || this.right.explode(depth + 1, (x) -> this.right = x);
        }

        public boolean split(Consumer<SnailfishNumberPair> parentSetter) {
            return this.left.split((x) -> {
                x.setParent(this);
                this.left = x;
            }) || this.right.split((x) -> {
                x.setParent(this);
                this.right = x;
            });
        }

        public int magnitude() {
            return 3 * getLeft().magnitude() + 2 * getRight().magnitude();
        }

        public SnailfishNumberPair clone() {
            var pair = new SnailfishNumberPair();
            pair.setLeft(this.getLeft().clone());
            pair.setRight(this.getRight().clone());
            pair.getLeft().setParent(pair);
            pair.getRight().setParent(pair);
            return pair;
        }

        public SnailfishRegularNumber leftAdjacent() {
            if (parent == null) {
                return null;
            }
            if (parent.getRight() == this) {
                var left = parent.getLeft();
                while (left instanceof SnailfishNumberPair pair) {
                    left = pair.getRight();
                }
                return (SnailfishRegularNumber) left;
            }

            return parent.leftAdjacent();
        }

        public SnailfishRegularNumber rightAdjacent() {
            if (parent == null) {
                return null;
            }
            if (parent.getLeft() == this) {
                var right = parent.getRight();
                while (right instanceof SnailfishNumberPair pair) {
                    right = pair.getLeft();
                }
                return (SnailfishRegularNumber) right;
            }

            return parent.rightAdjacent();
        }
    }

    @Data
    @AllArgsConstructor
    public static class SnailfishRegularNumber implements SnailfishNumber {
        private int value;

        public String toString() {
            return String.valueOf(value);
        }

        public void add(int value) {
            this.value += value;
        }

        public boolean explode(int depth, Consumer<SnailfishRegularNumber> parentSetter) {
            return false;
        }

        public boolean split(Consumer<SnailfishNumberPair> parentSetter) {
            if (value >= 10) {
                int left = (int) Math.floor(1d * value / 2);
                int right = value - left;
                var pair = new SnailfishNumberPair();
                pair.setLeft(new SnailfishRegularNumber(left));
                pair.setRight(new SnailfishRegularNumber(right));
                parentSetter.accept(pair);
                return true;
            }
            return false;
        }

        public void setParent(SnailfishNumberPair parent) {
            // not relevant
        }

        public int magnitude() {
            return getValue();
        }

        public SnailfishRegularNumber clone() {
            return new SnailfishRegularNumber(this.getValue());
        }
    }
}


