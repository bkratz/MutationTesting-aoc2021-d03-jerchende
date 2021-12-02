package net.erchen.adventofcode2021.day02;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NavigatorTest {


    @SneakyThrows
    static List<String> sampleInput() {
        return Files.readAllLines(Path.of("src/test/resources/day02/sample.txt"));
    }

    @SneakyThrows
    static List<String> solutionInput() {
        return Files.readAllLines(Path.of("src/test/resources/day02/input.txt"));
    }

    Navigator navigator;

    @BeforeEach
    void setUp() {
        navigator = new Navigator();
    }

    @Test
    void shouldCalculatePositionPart1_Sample() {
        navigator.applyCommandsPart1(Command.parseFromInput(sampleInput()));

        assertThat(navigator.getHorizontal().intValue()).isEqualTo(15);
        assertThat(navigator.getDepth().intValue()).isEqualTo(10);
    }

    @Test
    void shouldCalculatePositionPart1_Solution() {
        navigator.applyCommandsPart1(Command.parseFromInput(solutionInput()));

        assertThat(navigator.getHorizontal().intValue()).isEqualTo(1962);
        assertThat(navigator.getDepth().intValue()).isEqualTo(987);
    }

    @Test
    void shouldCalculatePositionPart2_Sample() {
        navigator.applyCommandsPart2(Command.parseFromInput(sampleInput()));

        assertThat(navigator.getHorizontal().intValue()).isEqualTo(15);
        assertThat(navigator.getDepth().intValue()).isEqualTo(60);
    }

    @Test
    void shouldCalculatePositionPart2_Solution() {
        navigator.applyCommandsPart2(Command.parseFromInput(solutionInput()));

        assertThat(navigator.getHorizontal().intValue()).isEqualTo(1962);
        assertThat(navigator.getDepth().intValue()).isEqualTo(1017893);
        assertThat(navigator.getAim().intValue()).isEqualTo(987);
    }
}