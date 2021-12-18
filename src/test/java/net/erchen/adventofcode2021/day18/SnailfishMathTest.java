package net.erchen.adventofcode2021.day18;

import lombok.SneakyThrows;
import net.erchen.adventofcode2021.day18.SnailfishMath.SnailfishNumberPair;
import net.erchen.adventofcode2021.day18.SnailfishMath.SnailfishRegularNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SnailfishMathTest {

    @SneakyThrows
    static List<String> solutionInput() {
        return Files.readAllLines(Path.of("src/test/resources/day18/input.txt"));
    }

    @Test
    void shouldParseNumbersAsTree() {

        var parsedTree = SnailfishMath.parseNumber("[[1,2],3]");

        assertThat(parsedTree).isInstanceOf(SnailfishNumberPair.class);
        assertThat(((SnailfishNumberPair) parsedTree).getLeft()).isInstanceOf(SnailfishNumberPair.class);
        assertThat(((SnailfishNumberPair) parsedTree).getRight()).isInstanceOf(SnailfishRegularNumber.class);

        var left = ((SnailfishNumberPair) ((SnailfishNumberPair) parsedTree).getLeft());
        var right = ((SnailfishRegularNumber) ((SnailfishNumberPair) parsedTree).getRight());

        assertThat(left.getLeft().toString()).isEqualTo("1");
        assertThat(left.getRight().toString()).isEqualTo("2");
        assertThat(left.getParent()).isEqualTo(parsedTree);

        assertThat(right.toString()).isEqualTo("3");
    }

    @ParameterizedTest
    @ValueSource(strings = { "[1,2]", "[[1,2],3]", "[9,[8,7]]", "[[1,9],[8,5]]", "[[[[1,2],[3,4]],[[5,6],[7,8]]],9]", "[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]", "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]" })
    void shouldParseNumbers(String number) {
        assertThat(SnailfishMath.parseNumber(number).toString()).isEqualTo(number);
    }

    @Test
    void shouldGetLeftAdjacent() {
        var parsedTree = (SnailfishNumberPair) SnailfishMath.parseNumber("[[1,2],[3,4]]");
        var left = (SnailfishNumberPair) parsedTree.getLeft();
        var right = (SnailfishNumberPair) parsedTree.getRight();

        assertThat(left.leftAdjacent()).isNull();
        assertThat(right.leftAdjacent()).isEqualTo(new SnailfishRegularNumber(2));
    }

    @Test
    void shouldGetRightAdjacent() {
        var parsedTree = (SnailfishNumberPair) SnailfishMath.parseNumber("[[1,2],[3,4]]");
        var left = (SnailfishNumberPair) parsedTree.getLeft();
        var right = (SnailfishNumberPair) parsedTree.getRight();

        assertThat(left.rightAdjacent()).isEqualTo(new SnailfishRegularNumber(3));
        assertThat(right.rightAdjacent()).isNull();
    }

    @ParameterizedTest
    @CsvSource(delimiter = '#', value = {
            "[[[[[9,8],1],2],3],4]#[[[[0,9],2],3],4]",
            "[7,[6,[5,[4,[3,2]]]]]#[7,[6,[5,[7,0]]]]",
            "[[6,[5,[4,[3,2]]]],1]#[[6,[5,[7,0]]],3]",
            "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]#[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]",
            "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]#[[3,[2,[8,0]]],[9,[5,[7,0]]]]",
    })
    void shouldExplode(String input, String expected) {
        var snailfishNumber = SnailfishMath.parseNumber(input);

        assertThat(snailfishNumber.explode(0, x -> {
        })).isTrue();
        assertThat(snailfishNumber.toString()).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '#', value = {
            "[[[[0,7],4],[15,[0,13]]],[1,1]]#[[[[0,7],4],[[7,8],[0,13]]],[1,1]]",
            "[[[[0,7],4],[[7,8],[0,13]]],[1,1]]#[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]",
    })
    void shouldSplit(String input, String expected) {
        var snailfishNumber = SnailfishMath.parseNumber(input);

        assertThat(snailfishNumber.split(x -> {
        })).isTrue();
        assertThat(snailfishNumber.toString()).isEqualTo(expected);
    }

    @Test
    void shouldReduce() {
        var reduced = SnailfishMath.reduce(SnailfishMath.parseNumber("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]"));

        assertThat(reduced.toString()).isEqualTo("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
    }

    @Test
    void shouldSumNumbers1() {
        var snailfishNumber = SnailfishMath.sumNumbers(List.of(
                "[1,1]",
                "[2,2]",
                "[3,3]",
                "[4,4]"
        ));

        assertThat(snailfishNumber.toString()).isEqualTo("[[[[1,1],[2,2]],[3,3]],[4,4]]");
    }

    @Test
    void shouldSumNumbers2() {
        var snailfishNumber = SnailfishMath.sumNumbers(List.of(
                "[1,1]",
                "[2,2]",
                "[3,3]",
                "[4,4]",
                "[5,5]"
        ));

        assertThat(snailfishNumber.toString()).isEqualTo("[[[[3,0],[5,3]],[4,4]],[5,5]]");
    }

    @Test
    void shouldSumNumbers3() {
        var snailfishNumber = SnailfishMath.sumNumbers(List.of(
                "[1,1]",
                "[2,2]",
                "[3,3]",
                "[4,4]",
                "[5,5]",
                "[6,6]"
        ));

        assertThat(snailfishNumber.toString()).isEqualTo("[[[[5,0],[7,4]],[5,5]],[6,6]]");
    }

    @Test
    void shouldSumNumbers4() {
        var snailfishNumber = SnailfishMath.sumNumbers(List.of(
                "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                "[7,[5,[[3,8],[1,4]]]]",
                "[[2,[2,2]],[8,[8,1]]]",
                "[2,9]",
                "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                "[[[5,[7,4]],7],1]",
                "[[[[4,2],2],6],[8,7]]"
        ));

        assertThat(snailfishNumber.toString()).isEqualTo("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
    }

    @Test
    void shouldSumNumbers5() {
        var snailfishNumber = SnailfishMath.sumNumbers(List.of(
                "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                "[[[[5,4],[7,7]],8],[[8,3],8]]",
                "[[9,3],[[9,9],[6,[4,9]]]]",
                "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        ));

        assertThat(snailfishNumber.toString()).isEqualTo("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]");
        assertThat(snailfishNumber.magnitude()).isEqualTo(4140);
    }

    @Test
    void shouldSumNumbers_Solution() {
        var snailfishNumber = SnailfishMath.sumNumbers(solutionInput());

        assertThat(snailfishNumber.toString()).isEqualTo("[[[[7,7],[7,7]],[[7,7],[7,0]]],[[[7,7],[8,8]],[[8,8],[9,8]]]]");
        assertThat(snailfishNumber.magnitude()).isEqualTo(4391);
    }

    @Test
    void shouldCalculateLargestMagnitudeFromTwoAdditions() {
        int highestMagnitude = SnailfishMath.findHighestMagnitude(List.of(
                "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                "[[[[5,4],[7,7]],8],[[8,3],8]]",
                "[[9,3],[[9,9],[6,[4,9]]]]",
                "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        ));

        assertThat(highestMagnitude).isEqualTo(3993);
    }

    @Test
    void shouldCalculateLargestMagnitudeFromTwoAdditions_Solution() {
        assertThat(SnailfishMath.findHighestMagnitude(solutionInput())).isEqualTo(4626);
    }
}