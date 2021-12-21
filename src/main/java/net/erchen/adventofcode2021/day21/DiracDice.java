package net.erchen.adventofcode2021.day21;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class DiracDice {

    private final Player player1;
    private final Player player2;
    private final Dice dice;
    private final int pointsToWin;

    public DiracDice(int startingPositionPlayer1, int startingPositionPlayer2) {
        player1 = Player.builder().position(startingPositionPlayer1).build();
        player2 = Player.builder().position(startingPositionPlayer2).build();
        dice = new Dice();
        pointsToWin = 1000;
    }

    public void playUntilPlayerHas1000Points() {
        while (player1.move(dice.rollTreeTimes()) < pointsToWin && player2.move(dice.rollTreeTimes()) < pointsToWin) {
            // empty
        }
    }

    @Getter
    public static class Dice {
        private int rolls;
        private int value;
        private final int maxValue = 100;

        public int roll() {
            rolls++;
            value = value == maxValue ? 1 : value + 1;
            return value;
        }

        public int rollTreeTimes() {
            return roll() + roll() + roll();
        }
    }

    @Getter
    @Builder
    @EqualsAndHashCode
    public static class Player {
        private int points;
        private int position;

        public int move(int diceValue) {
            position = (position + diceValue - 1) % 10 + 1;
            points += position;
            return points;
        }
    }

}
