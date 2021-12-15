package net.erchen.adventofcode2021.day15;

import lombok.SneakyThrows;
import net.erchen.adventofcode2021.common.Matrix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static net.erchen.adventofcode2021.day15.Chiton.multipleMap;
import static org.assertj.core.api.Assertions.assertThat;

class ChitonTest {

    @SneakyThrows
    static String sampleInput() {
        return Files.readString(Path.of("src/test/resources/day15/sample.txt"));
    }

    @SneakyThrows
    static String sampleBigInput() {
        return Files.readString(Path.of("src/test/resources/day15/sample_big.txt"));
    }

    @SneakyThrows
    static String solutionInput() {
        return Files.readString(Path.of("src/test/resources/day15/input.txt"));
    }

    @Test
    void shouldFindBestRoute() {
        var chiton = Chiton.fromInput("""
            1222
            1222
            1111
            2221""");

        assertThat(chiton.bestRoute()).extracting(Matrix.Field::toString).containsExactly(
            "Matrix.Field(x=0, y=0)",
            "Matrix.Field(x=0, y=1)",
            "Matrix.Field(x=0, y=2)",
            "Matrix.Field(x=1, y=2)",
            "Matrix.Field(x=2, y=2)",
            "Matrix.Field(x=3, y=2)",
            "Matrix.Field(x=3, y=3)");
    }

    @Test
    void shouldFindBestRoute_Sample() {
        var chiton = Chiton.fromInput(sampleInput());

        assertThat(chiton.bestRoute()).extracting(Matrix.Field::toString).containsExactly(
            "Matrix.Field(x=0, y=0)",
            "Matrix.Field(x=0, y=1)",
            "Matrix.Field(x=0, y=2)",
            "Matrix.Field(x=1, y=2)",
            "Matrix.Field(x=2, y=2)",
            "Matrix.Field(x=3, y=2)",
            "Matrix.Field(x=4, y=2)",
            "Matrix.Field(x=5, y=2)",
            "Matrix.Field(x=6, y=2)",
            "Matrix.Field(x=6, y=3)",
            "Matrix.Field(x=7, y=3)",
            "Matrix.Field(x=7, y=4)",
            "Matrix.Field(x=8, y=4)",
            "Matrix.Field(x=8, y=5)",
            "Matrix.Field(x=8, y=6)",
            "Matrix.Field(x=8, y=7)",
            "Matrix.Field(x=8, y=8)",
            "Matrix.Field(x=9, y=8)",
            "Matrix.Field(x=9, y=9)");
    }

    @Test
    void shouldCalculateRisk_Sample() {
        var chiton = Chiton.fromInput(sampleInput());

        assertThat(chiton.calculateMinimalRisk()).isEqualTo(40);
    }

    @Test
    void shouldCalculateRisk_SampleBig() {
        var chiton = Chiton.fromInput(sampleBigInput());

        assertThat(chiton.calculateMinimalRisk()).isEqualTo(315);
    }

    @Test
    void shouldMultipleMap() {
        assertThat(multipleMap("8")).isEqualTo("""
            89123
            91234
            12345
            23456
            34567""");

        assertThat(multipleMap(sampleInput())).isEqualTo(sampleBigInput());
    }

    @Test
    void shouldCalculateRisk_Solution() {
        var chiton = Chiton.fromInput(solutionInput());

        assertThat(chiton.calculateMinimalRisk()).isEqualTo(720);
    }

    @Test
    //@Disabled
    @Timeout(value = 15, unit = TimeUnit.MINUTES)
    void shouldCalculateRiskBig_Solution() {
        var chiton = Chiton.fromInput(multipleMap(solutionInput()));

        assertThat(chiton.calculateMinimalRisk()).isEqualTo(3025);
    }
}