package net.erchen.adventofcode2021.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiracDiceTest {

    @Test
    void playUntilPlayerHas1000Points_Sample() {
        var game = new DiracDice(4, 8);
        game.playUntilPlayerHas1000Points();

        assertThat(game.getPlayer1().getPoints()).isEqualTo(1000);
        assertThat(game.getPlayer2().getPoints()).isEqualTo(745);
        assertThat(game.getDice().getRolls()).isEqualTo(993);
    }

    @Test
    void playUntilPlayerHas1000Points_Solution() {
        var game = new DiracDice(7, 4);
        game.playUntilPlayerHas1000Points();

        assertThat(game.getPlayer1().getPoints()).isEqualTo(1008);
        assertThat(game.getPlayer2().getPoints()).isEqualTo(784);
        assertThat(game.getDice().getRolls()).isEqualTo(861);
    }

}