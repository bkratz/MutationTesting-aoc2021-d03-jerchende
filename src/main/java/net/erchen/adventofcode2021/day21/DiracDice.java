package net.erchen.adventofcode2021.day21;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiracDice {

    private Player player1;
    private Player player2;
    private Dice dice;
    private int pointsToWin;

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
    @Builder
    public static class Player {
        private int points;
        private int position;

        public int move(int diceValue) {
            position = (position + diceValue - 1) % 10 + 1;
            points += position;
            return points;
        }
    }

    @Getter
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
