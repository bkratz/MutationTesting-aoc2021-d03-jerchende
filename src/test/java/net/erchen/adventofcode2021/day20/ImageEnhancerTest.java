package net.erchen.adventofcode2021.day20;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ImageEnhancerTest {

    @SneakyThrows
    static String sampleInput() {
        return Files.readString(Path.of("src/test/resources/day20/sample.txt"));
    }

    @SneakyThrows
    static String solutionInput() {
        return Files.readString(Path.of("src/test/resources/day20/input.txt"));
    }

    @Test
    void shouldParse() {

        var imageEnhancer = ImageEnhancer.fromInput(sampleInput());

        assertThat(imageEnhancer.getAlgorithm()).hasSize(512);
        assertThat(imageEnhancer.getImage().toString()).isEqualTo("""
                # . . # .
                # . . . .
                # # . . #
                . . # . .
                . . # # #""");

    }

    @Test
    void shouldEnhance() {

        var imageEnhancer = ImageEnhancer.fromInput(sampleInput());

        imageEnhancer.enhance();
        assertThat(imageEnhancer.getImage().toString()).isEqualTo("""
                . . . . . . . . .
                . . # # . # # . .
                . # . . # . # . .
                . # # . # . . # .
                . # # # # . . # .
                . . # . . # # . .
                . . . # # . . # .
                . . . . # . # . .
                . . . . . . . . .""");
    }

    @Test
    void shouldEnhanceTwice() {

        var imageEnhancer = ImageEnhancer.fromInput(sampleInput());

        imageEnhancer.enhance(2);
        assertThat(imageEnhancer.getImage().toString()).isEqualTo("""
                . . . . . . . . . . . . .
                . . . . . . . . . . . . .
                . . . . . . . . . # . . .
                . . . # . . # . # . . . .
                . . # . # . . . # # # . .
                . . # . . . # # . # . . .
                . . # . . . . . # . # . .
                . . . # . # # # # # . . .
                . . . . # . # # # # # . .
                . . . . . # # . # # . . .
                . . . . . . # # # . . . .
                . . . . . . . . . . . . .
                . . . . . . . . . . . . .""");
    }

    @Test
    void shouldCountLitPixels_Sample() {

        var imageEnhancer = ImageEnhancer.fromInput(sampleInput());

        imageEnhancer.enhance(2);

        assertThat(imageEnhancer.countLightPixels()).isEqualTo(35);
    }

    @Test
    void shouldCountLitPixels_Solution() {

        var imageEnhancer = ImageEnhancer.fromInput(solutionInput());

        imageEnhancer.enhance(2);

        assertThat(imageEnhancer.countLightPixels()).isEqualTo(5819L);
    }

    @Test
    void shouldCountLitPixelsPart2_Sample() {

        var imageEnhancer = ImageEnhancer.fromInput(sampleInput());

        imageEnhancer.enhance(50);

        assertThat(imageEnhancer.countLightPixels()).isEqualTo(3351L);
    }

    @Test
    void shouldCountLitPixelsPart2_Solution() {

        var imageEnhancer = ImageEnhancer.fromInput(solutionInput());

        imageEnhancer.enhance(50);

        assertThat(imageEnhancer.countLightPixels()).isEqualTo(18516L);
    }
}