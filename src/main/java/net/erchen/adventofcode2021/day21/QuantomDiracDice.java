package net.erchen.adventofcode2021.day21;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
public class QuantomDiracDice {

    private Map<Game, BigDecimal> games;
    private final Map<Integer, Integer> diceValues = Map.of(
            3, 1,
            4, 3,
            5, 6,
            6, 7,
            7, 6,
            8, 3,
            9, 1
    );

    public QuantomDiracDice(int startingPositionPlayer1, int startingPositionPlayer2) {
        var game = Game.builder()
                .player1(Player.builder().position(startingPositionPlayer1).build())
                .player2(Player.builder().position(startingPositionPlayer2).build())
                .round(1)
                .build();
        this.games = new HashMap<>(Map.of(game, BigDecimal.valueOf(1L)));
    }

    public void play() {
        while (games.keySet().stream().anyMatch(game -> !game.isFinished())) {
            var newGames = new HashMap<Game, BigDecimal>();
            for (Game game : games.keySet()) {
                playGame(game, newGames);
            }
            games = newGames;
        }
    }

    private void playGame(Game game, Map<Game, BigDecimal> newGames) {
        var currentCount = games.get(game);

        if (game.isFinished()) {
            newGames.merge(game, currentCount, BigDecimal::add);
            return;
        }

        diceValues.forEach((dice, count) -> newGames.merge(game.move(dice), currentCount.multiply(BigDecimal.valueOf(count)), BigDecimal::add));
    }

    public long wonGamesForPlayer1() {
        return games.entrySet().stream().filter(entry -> entry.getKey().player1().points() >= 21).mapToLong(entry -> entry.getValue().longValue()).sum();
    }

    public long wonGamesForPlayer2() {
        return games.entrySet().stream().filter(entry -> entry.getKey().player2().points() >= 21).mapToLong(entry -> entry.getValue().longValue()).sum();
    }

    public static record Game(Player player1, Player player2, int round) {
        @Builder
        public Game {
        }

        public boolean isFinished() {
            return player1.points >= 21 || player2.points >= 21;
        }

        public Game move(int dice) {
            if (round % 2 == 1) {
                return new Game(player1.move(dice), player2, round + 1);
            } else {
                return new Game(player1, player2.move(dice), round + 1);
            }
        }
    }

    public static record Player(int points, int position) {
        @Builder
        public Player {
        }

        public Player move(int diceValue) {
            var newPosition = (position + diceValue - 1) % 10 + 1;
            var newPoints = points + newPosition;
            return new Player(newPoints, newPosition);
        }
    }

}
