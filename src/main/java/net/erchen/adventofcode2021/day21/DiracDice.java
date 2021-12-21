package net.erchen.adventofcode2021.day21;

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

}
