package net.erchen.adventofcode2021.day11;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class DumboOctopusTest {


    @SneakyThrows
    static String sampleInput() {
        return Files.readString(Path.of("src/test/resources/day11/sample.txt"));
    }

    @SneakyThrows
    static String solutionInput() {
        return Files.readString(Path.of("src/test/resources/day11/input.txt"));
    }

    @Test
    void sample() {
        DumboOctopus octopus = DumboOctopus.fromInput("""
            11111
            19991
            19191
            19991
            11111""");

        assertThat(octopus.getOctopuses().toString()).isEqualTo("""
            1 1 1 1 1
            1 9 9 9 1
            1 9 1 9 1
            1 9 9 9 1
            1 1 1 1 1""");

        octopus.simulateSteps(1);

        assertThat(octopus.getFlashes()).isEqualTo(9);
        assertThat(octopus.getOctopuses().toString()).isEqualTo("""
            3 4 5 4 3
            4 0 0 0 4
            5 0 0 0 5
            4 0 0 0 4
            3 4 5 4 3""");

        octopus.simulateSteps(1);
        assertThat(octopus.getFlashes()).isEqualTo(9);
        assertThat(octopus.getOctopuses().toString()).isEqualTo("""
            4 5 6 5 4
            5 1 1 1 5
            6 1 1 1 6
            5 1 1 1 5
            4 5 6 5 4""");

    }

    @Test
    void countFlashes_Sample() {
        DumboOctopus octopus = DumboOctopus.fromInput(sampleInput());
        octopus.simulateSteps(100);
        assertThat(octopus.getFlashes()).isEqualTo(1656);
    }

    @Test
    void countFlashes_Solution() {
        DumboOctopus octopus = DumboOctopus.fromInput(solutionInput());
        octopus.simulateSteps(100);
        assertThat(octopus.getFlashes()).isEqualTo(1732);
    }

    @Test
    void shouldFlashesAll() {
        DumboOctopus octopus = DumboOctopus.fromInput(sampleInput());
        octopus.simulateSteps(195);
        assertThat(octopus.getOctopuses().toString()).isEqualTo("""
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0""");
    }

    @Test
    void shouldSimulateUntilAllFlashes_Sample() {
        DumboOctopus octopus = DumboOctopus.fromInput(sampleInput());
        assertThat(octopus.simulateUntilAllFlashes()).isEqualTo(195);

        assertThat(octopus.getOctopuses().toString()).isEqualTo("""
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0""");
    }

    @Test
    void shouldSimulateUntilAllFlashes_Solution() {
        DumboOctopus octopus = DumboOctopus.fromInput(solutionInput());
        assertThat(octopus.simulateUntilAllFlashes()).isEqualTo(290);

        assertThat(octopus.getOctopuses().toString()).isEqualTo("""
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0""");
    }
}