package net.erchen.adventofcode2021.day14;

import lombok.SneakyThrows;
import net.erchen.adventofcode2021.day14.Polymerization.Pair;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

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

        assertThat(polymerization.getPairs())
            .containsEntry(new Pair('N', 'N'), 1L)
            .containsEntry(new Pair('N', 'C'), 1L)
            .containsEntry(new Pair('C', 'B'), 1L);

        assertThat(polymerization.getInstructions()).containsEntry(new Pair('C', 'H'), List.of(new Pair('C', 'B'), new Pair('B', 'H')));
        assertThat(polymerization.getInstructions()).containsEntry(new Pair('C', 'N'), List.of(new Pair('C', 'C'), new Pair('C', 'N')));

    }

    @Test
    void shouldDoInsertion() {
        var polymerization = Polymerization.fromInput(sampleInput());

        polymerization.doInsertion();
        assertThat(polymerization.getPairs())
            .containsEntry(new Pair('N', 'C'), 1L)
            .containsEntry(new Pair('C', 'N'), 1L)
            .containsEntry(new Pair('N', 'B'), 1L)
            .containsEntry(new Pair('B', 'C'), 1L)
            .containsEntry(new Pair('C', 'H'), 1L)
            .containsEntry(new Pair('H', 'B'), 1L);

        polymerization.doInsertion();
        assertThat(polymerization.getPairs()) // "NBCCNBBBCBHCB"
            .containsEntry(new Pair('N', 'B'), 2L)
            .containsEntry(new Pair('B', 'C'), 2L)
            .containsEntry(new Pair('C', 'C'), 1L)
            .containsEntry(new Pair('C', 'N'), 1L)
            .containsEntry(new Pair('B', 'B'), 2L)
            .containsEntry(new Pair('C', 'B'), 2L)
            .containsEntry(new Pair('B', 'H'), 1L)
            .containsEntry(new Pair('H', 'C'), 1L);
    }

    @Test
    void shouldCountOccurrencesIgnoringFirst() {
        var polymerization = Polymerization.fromInput(sampleInput());

        var occurrences = polymerization.countOccurrencesIgnoringFirst();
        assertThat(occurrences) // NNCB
            .containsEntry('N', 1L)
            .containsEntry('C', 1L)
            .containsEntry('B', 1L);
    }

    @Test
    void shouldDoInsertion10Times() {
        var polymerization = Polymerization.fromInput(sampleInput());

        polymerization.doInsertion(10);
        var occurrences = polymerization.countOccurrencesIgnoringFirst();
        assertThat(occurrences)
            .containsEntry('B', 1749L)
            .containsEntry('C', 298L)
            .containsEntry('H', 161L)
            .containsEntry('N', 864L);
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
        assertThat(polymerization.getMagicNumber()).isEqualTo(2827627697643L);
    }
}