package net.erchen.adventofcode2021.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuantomDiracDiceTest {

    @Test
    void playSample() {
        var diracDice = new QuantomDiracDice(4, 8);

        diracDice.play();

        assertThat(diracDice.wonGamesForPlayer1()).isEqualTo(444356092776315L);
        assertThat(diracDice.wonGamesForPlayer2()).isEqualTo(341960390180808L);
    }

    @Test
    void playSolution() {
        var diracDice = new QuantomDiracDice(7, 4);

        diracDice.play();

        assertThat(diracDice.wonGamesForPlayer1()).isEqualTo(570239341223618L);
        assertThat(diracDice.wonGamesForPlayer2()).isEqualTo(371697814511699L);
    }
}