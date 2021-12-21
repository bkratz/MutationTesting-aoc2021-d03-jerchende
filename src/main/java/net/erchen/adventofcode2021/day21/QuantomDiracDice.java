package net.erchen.adventofcode2021.day21;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class QuantomDiracDice {

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class Game {
        private Player player1;
        private Player player2;
        private Dice dice;
    }

    @Getter
    @EqualsAndHashCode
    public static class Dice {
        private int rolls;
        private int value;

        public int roll() {
            rolls++;
            value = value == 100 ? 1 : value + 1;
            return value;
        }

        public int rollTreeTimes() {
            return roll() + roll() + roll();
        }
    }

}
