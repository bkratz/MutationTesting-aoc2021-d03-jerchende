package net.erchen.adventofcode2021.day03;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class DiagnosticReportTest {

    @SneakyThrows
    static List<Integer> sampleInput() {
        return Files.readAllLines(Path.of("src/test/resources/day03/sample.txt")).stream().map(s -> Integer.parseInt(s, 2)).collect(toList());
    }

    @SneakyThrows
    static List<Integer> solutionInput() {
        return Files.readAllLines(Path.of("src/test/resources/day03/input.txt")).stream().map(s -> Integer.parseInt(s, 2)).collect(toList());
    }

    @Test
    void shouldCalculatePowerConsumption_Sample() {
        var diagnosticReport = DiagnosticReport.builder().report(sampleInput()).reportLength(5).build();

        assertThat(diagnosticReport.calculateGammaRate()).isEqualTo(22);
        assertThat(diagnosticReport.calculateEpsilonRate()).isEqualTo(9);
        assertThat(diagnosticReport.calculatePowerConsumption()).isEqualTo(198);
    }

    @Test
    void shouldCalculateLifeSupportRating_Sample() {
        var diagnosticReport = DiagnosticReport.builder().report(sampleInput()).reportLength(5).build();

        assertThat(diagnosticReport.calculateOxygenGeneratorRating()).isEqualTo(23);
        assertThat(diagnosticReport.calculateCo2ScrubberRating()).isEqualTo(10);
        assertThat(diagnosticReport.calculateLifeSupportRating()).isEqualTo(230);
    }

    @Test
    void shouldCalculatePowerConsumption_Solution() {
        var diagnosticReport = DiagnosticReport.builder().report(solutionInput()).reportLength(12).build();

        assertThat(diagnosticReport.calculateGammaRate()).isEqualTo(217);
        assertThat(diagnosticReport.calculateEpsilonRate()).isEqualTo(3878);
        assertThat(diagnosticReport.calculatePowerConsumption()).isEqualTo(841526);
    }


    @Test
    void shouldCalculateLifeSupportRating_Solution() {
        var diagnosticReport = DiagnosticReport.builder().report(solutionInput()).reportLength(12).build();

        assertThat(diagnosticReport.calculateOxygenGeneratorRating()).isEqualTo(1177);
        assertThat(diagnosticReport.calculateCo2ScrubberRating()).isEqualTo(4070);
        assertThat(diagnosticReport.calculateLifeSupportRating()).isEqualTo(4790390);
    }

}