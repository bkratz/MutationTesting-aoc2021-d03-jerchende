package net.erchen.adventofcode2021.day08;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SevenSegmentDisplayTest {

    @SneakyThrows
    static List<String> sampleInput() {
        return Files.readAllLines(Path.of("src/test/resources/day08/sample.txt")).stream().toList();
    }

    @SneakyThrows
    static List<String> solutionInput() {
        return Files.readAllLines(Path.of("src/test/resources/day08/input.txt")).stream().toList();
    }

    @Test
    void shouldCountUniqueResults_Sample() {
        assertThat(SevenSegmentDisplay.countUniqueResults(sampleInput())).isEqualTo(26);
    }

    @Test
    void shouldCountUniqueResults_Solution() {
        assertThat(SevenSegmentDisplay.countUniqueResults(solutionInput())).isEqualTo(554);
    }

    @Test
    void shouldSumOutputValues_Sample() {
        assertThat(SevenSegmentDisplay.sumOutputValues(sampleInput())).isEqualTo(61229);
    }

    @Test
    void shouldSumOutputValues_Solution() {
        assertThat(SevenSegmentDisplay.sumOutputValues(solutionInput())).isEqualTo(990964);
    }

    @Test
    void shouldDecode() {
        assertThat(SevenSegmentDisplay.decode("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab", "cdfeb fcadb cdfeb cdbaf")).isEqualTo(5353);
    }

}