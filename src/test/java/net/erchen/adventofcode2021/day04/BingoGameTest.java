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
    void shouldFirstWinning_Sample() {
        var bingoGame = BingoGame.fromInput(sampleInput());

        var magicNumber = bingoGame.getFirstWinning();
        assertThat(magicNumber).isEqualTo(4512);
    }

    @Test
    void shouldFirstWinning_Solution() {
        var bingoGame = BingoGame.fromInput(solutionInput());

        var magicNumber = bingoGame.getFirstWinning();
        assertThat(magicNumber).isEqualTo(60368);
    }

    @Test
    void shouldLastWinning_Sample() {
        var bingoGame = BingoGame.fromInput(sampleInput());

        var magicNumber = bingoGame.getLastWinning();
        assertThat(magicNumber).isEqualTo(1924);
    }

    @Test
    void shouldLastWinning_Solution() {
        var bingoGame = BingoGame.fromInput(solutionInput());

        var magicNumber = bingoGame.getLastWinning();
        assertThat(magicNumber).isEqualTo(17435);
    }
}