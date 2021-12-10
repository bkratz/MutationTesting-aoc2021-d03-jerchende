package net.erchen.adventofcode2021.day10;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SyntaxCheckerTest {

    @SneakyThrows
    static List<String> sampleInput() {
        return Files.readAllLines(Path.of("src/test/resources/day10/sample.txt")).stream().toList();
    }

    @SneakyThrows
    static List<String> solutionInput() {
        return Files.readAllLines(Path.of("src/test/resources/day10/input.txt")).stream().toList();
    }

    @Test
    void shouldCalcSyntaxErrorScore_Sample() {
        assertThat(SyntaxChecker.calcSyntaxErrorScore(sampleInput())).isEqualTo(26397);
    }

    @Test
    void shouldCalcSyntaxErrorScore_Solution() {
        assertThat(SyntaxChecker.calcSyntaxErrorScore(solutionInput())).isEqualTo(469755);
    }

    @Test
    void shouldCalcAutocompleteScore_Sample() {
        assertThat(SyntaxChecker.calcAutocompleteScore(sampleInput())).isEqualTo(288957);
    }

    @Test
    void shouldCalcAutocompleteScore_Solution() {
        assertThat(SyntaxChecker.calcAutocompleteScore(solutionInput())).isEqualTo(2762335572L);
    }
}