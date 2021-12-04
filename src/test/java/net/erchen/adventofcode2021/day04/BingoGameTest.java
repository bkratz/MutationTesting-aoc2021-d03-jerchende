package net.erchen.adventofcode2021.day04;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class BingoGameTest {

    @SneakyThrows
    static String sampleInput() {
        return Files.readString(Path.of("src/test/resources/day04/sample.txt"));
    }

    @SneakyThrows
    static String solutionInput() {
        return Files.readString(Path.of("src/test/resources/day04/input.txt"));
    }

    @Test
    void shouldParseInput() {
        var bingoGame = BingoGame.fromInput(sampleInput());

        assertThat(bingoGame.getNumbers()).containsExactly(7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21, 24, 10, 16, 13, 6, 15, 25, 12, 22, 18, 20, 8, 19, 3, 26, 1);
        assertThat(bingoGame.getDecks()).hasSize(3);
    }
}