package net.erchen.adventofcode2021.day14;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static net.erchen.adventofcode2021.day14.Polymerization.pair;
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
            .containsEntry(pair('N', 'N'), 1L)
            .containsEntry(pair('N', 'C'), 1L)
            .containsEntry(pair('C', 'B'), 1L);

        assertThat(polymerization.getInstructions()).containsEntry(pair('C', 'H'), List.of(pair('C', 'B'), pair('B', 'H')));
        assertThat(polymerization.getInstructions()).containsEntry(pair('C', 'N'), List.of(pair('C', 'C'), pair('C', 'N')));

    }

    @Test
    void shouldDoInsertion() {
        var polymerization = Polymerization.fromInput(sampleInput());

        polymerization.doInsertion();
        assertThat(polymerization.getPairs())
            .containsEntry(pair('N', 'C'), 1L)
            .containsEntry(pair('C', 'N'), 1L)
            .containsEntry(pair('N', 'B'), 1L)
            .containsEntry(pair('B', 'C'), 1L)
            .containsEntry(pair('C', 'H'), 1L)
            .containsEntry(pair('H', 'B'), 1L);

        polymerization.doInsertion();
        assertThat(polymerization.getPairs()) // "NBCCNBBBCBHCB"
            .containsEntry(pair('N', 'B'), 2L)
            .containsEntry(pair('B', 'C'), 2L)
            .containsEntry(pair('C', 'C'), 1L)
            .containsEntry(pair('C', 'N'), 1L)
            .containsEntry(pair('B', 'B'), 2L)
            .containsEntry(pair('C', 'B'), 2L)
            .containsEntry(pair('B', 'H'), 1L)
            .containsEntry(pair('H', 'C'), 1L);
    }

    @Test
    void shouldCountOccurrencesIgnoringFirst() {
        var polymerization = Polymerization.fromInput(sampleInput());

        var occurrences = polymerization.countOccurrencesIgnoringFirst();
        assertThat(occurrences) // NNCB
            .containsEntry((int) 'N', 1L)
            .containsEntry((int) 'C', 1L)
            .containsEntry((int) 'B', 1L);
    }

    @Test
    void shouldDoInsertion10Times() {
        var polymerization = Polymerization.fromInput(sampleInput());

        polymerization.doInsertion(10);
        var occurrences = polymerization.countOccurrencesIgnoringFirst();
        assertThat(occurrences)
            .containsEntry((int) 'B', 1749L)
            .containsEntry((int) 'C', 298L)
            .containsEntry((int) 'H', 161L)
            .containsEntry((int) 'N', 864L);
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