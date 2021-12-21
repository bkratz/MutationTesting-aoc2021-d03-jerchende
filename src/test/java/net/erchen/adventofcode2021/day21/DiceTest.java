package net.erchen.adventofcode2021.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DiceTest {

    @Test
    void shouldRollDeterministicDice() {
        var dice = new Dice();

        assertThat(dice.roll()).isEqualTo(1);
        assertThat(dice.roll()).isEqualTo(2);
        assertThat(dice.roll()).isEqualTo(3);

        assertThat(dice.rollTreeTimes()).isEqualTo(15);

        assertThat(dice.getRolls()).isEqualTo(6);
    }

}