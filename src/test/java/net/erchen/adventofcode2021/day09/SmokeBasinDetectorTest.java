package net.erchen.adventofcode2021.day09;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class SmokeBasinDetectorTest {

    @SneakyThrows
    static String sampleInput() {
        return Files.readString(Path.of("src/test/resources/day09/sample.txt"));
    }

    @SneakyThrows
    static String solutionInput() {
        return Files.readString(Path.of("src/test/resources/day09/input.txt"));
    }

    @Test
    void shouldCalculateRiskLevel_Sample() {
        var smokeBasinDetector = new SmokeBasinDetector(sampleInput());
        assertThat(smokeBasinDetector.calculateRiskLevel()).isEqualTo(15);
    }

    @Test
    void shouldCalculateRiskLevel_Solution() {
        var smokeBasinDetector = new SmokeBasinDetector(solutionInput());
        assertThat(smokeBasinDetector.calculateRiskLevel()).isEqualTo(465);
    }

    @Test
    void shouldCalculateLargestBasinsSize_Sample() {
        var smokeBasinDetector = new SmokeBasinDetector(sampleInput());
        assertThat(smokeBasinDetector.largestBasinsSize()).isEqualTo(1134);
    }

    @Test
    void shouldCalculateLargestBasinsSize_Solution() {
        var smokeBasinDetector = new SmokeBasinDetector(solutionInput());
        assertThat(smokeBasinDetector.largestBasinsSize()).isEqualTo(1269555);
    }
}