package net.erchen.adventofcode2021.day25;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class SeaCucumberTest {

    @SneakyThrows
    static String sampleInput() {
        return Files.readString(Path.of("src/test/resources/day25/sample.txt"));
    }

    @SneakyThrows
    static String soulutionInput() {
        return Files.readString(Path.of("src/test/resources/day25/input.txt"));
    }

    @Test
    void shouldConstruct() {
        var seaCucumber = new SeaCucumber(sampleInput());

        assertThat(seaCucumber.getOcean().toString()).isEqualTo("""
                v . . . > > . v v > 
                . v v > > . v v . . 
                > > . > v > . . . v 
                > > v > > . > . v . 
                v > v . v v . v . . 
                > . > > . . v . . . 
                . v v . . > . > v . 
                v . v . . > > v . v 
                . . . . v . . v . > """);
    }

    @Test
    void shouldMoveSingleSimple() {
        var seaCucumber = new SeaCucumber("...>>>>>...");

        seaCucumber.move();
        assertThat(seaCucumber.getOcean().toString()).isEqualTo(". . . > > > > . > . .");

        seaCucumber.move();
        assertThat(seaCucumber.getOcean().toString()).isEqualTo(". . . > > > . > . > .");
    }

    @Test
    void shouldMoveSingleSimple2() {
        var seaCucumber = new SeaCucumber("""
                ..........
                .>v....v..
                .......>..
                ..........""");

        seaCucumber.move();
        assertThat(seaCucumber.getOcean().toString()).isEqualTo("""
                . . . . . . . . . . 
                . > . . . . . . . . 
                . . v . . . . v > . 
                . . . . . . . . . . """);
    }

    @Test
    void shouldMoveSingle() {
        var seaCucumber = new SeaCucumber(sampleInput());

        assertThat(seaCucumber.move()).isTrue();
        assertThat(seaCucumber.getOcean().toString()).isEqualTo("""
                . . . . > . > v . > 
                v . v > . > v . v . 
                > v > > . . > v . . 
                > > v > v > . > . v 
                . > v . v . . . v . 
                v > > . > v v v . . 
                . . v . . . > > . . 
                v v . . . > > v v . 
                > . v . v . . v . v """);
    }

    @Test
    void shouldMoveUntilNoMoreMove_Sample() {
        var seaCucumber = new SeaCucumber(sampleInput());

        var moves = seaCucumber.moveUntilNoMoreMoves();
        assertThat(moves).isEqualTo(58);
    }

    @Test
    void shouldMoveUntilNoMoreMove_Solution() {
        var seaCucumber = new SeaCucumber(soulutionInput());

        var moves = seaCucumber.moveUntilNoMoreMoves();
        assertThat(moves).isEqualTo(400);
    }
}