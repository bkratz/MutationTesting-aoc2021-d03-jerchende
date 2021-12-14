package net.erchen.adventofcode2021.day14;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class PolymerizationTest {

    @SneakyThrows
    static String sampleInput() {
        return Files.readString(Path.of("src/test/resources/day14/sample.txt"));
    }

    @SneakyThrows
    static String solutionInput() {
        return Files.readString(Path.of("src/test/resources/day14/input.txt"));
    }

    @Test
    void shouldCreateFromInput() {

        var polymerization = Polymerization.fromInput(sampleInput());

        assertThat(polymerization.getTemplate()).isEqualTo("NNCB");
        assertThat(polymerization.getInstructions()).containsEntry('C' << 16 | 'H', 'B');
        assertThat(polymerization.getInstructions()).containsEntry('C' << 16 | 'N', 'C');
    }

    @Test
    void shouldDoInsertion() {
        var polymerization = Polymerization.fromInput(sampleInput());

        polymerization.doInsertion();
        assertThat(polymerization.getTemplate()).isEqualTo("NCNBCHB");

        polymerization.doInsertion();
        assertThat(polymerization.getTemplate()).isEqualTo("NBCCNBBBCBHCB");

        polymerization.doInsertion();
        assertThat(polymerization.getTemplate()).isEqualTo("NBBBCNCCNBBNBNBBCHBHHBCHB");

        polymerization.doInsertion();
        assertThat(polymerization.getTemplate()).isEqualTo("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB");
    }

    @Test
    void shouldDoInsertion10Times() {
        var polymerization = Polymerization.fromInput(sampleInput());

        polymerization.doInsertion(10);
        assertThat(polymerization.getTemplate()).hasSize(3073);
        var occurrences = polymerization.countOccurrences();
        assertThat(occurrences)
            .containsEntry((int) 'B', 1749L)
            .containsEntry((int) 'C', 298L)
            .containsEntry((int) 'H', 161L)
            .containsEntry((int) 'N', 865L);
    }

    @Test
    void shouldCalcMagicNumberAfter10Times_Sample() {
        var polymerization = Polymerization.fromInput(sampleInput());

        polymerization.doInsertion(10);
        assertThat(polymerization.getMagicNumber()).isEqualTo(1588L);
    }

    @Test
    void shouldCalcMagicNumberAfter10Times_Solution() {
        var polymerization = Polymerization.fromInput(solutionInput());

        polymerization.doInsertion(10);
        assertThat(polymerization.getMagicNumber()).isEqualTo(2509L);
    }

    @Test
    void shouldCalcMagicNumberAfter40Times_Sample() {
        var polymerization = Polymerization.fromInput(sampleInput());

        polymerization.doInsertion(40);
        assertThat(polymerization.getMagicNumber()).isEqualTo(2188189693529L);
    }

    @Test
    void shouldCalcMagicNumberAfter40Times_Solution() {
        var polymerization = Polymerization.fromInput(solutionInput());

        polymerization.doInsertion(40);
        assertThat(polymerization.getMagicNumber()).isEqualTo(2509L);
    }
}