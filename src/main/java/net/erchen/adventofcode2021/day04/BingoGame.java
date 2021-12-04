package net.erchen.adventofcode2021.day04;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import net.erchen.adventofcode2021.common.parser.SeparatorParser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class BingoGame {

    private final LinkedList<Map.Entry<BingoDeck, Integer>> winningDecks;

    public static BingoGame fromInput(String input) {
        var inputs = input.split("\n\n", 2);
        var numbers = Arrays.stream(inputs[0].split(",")).map(Integer::parseInt).collect(toList());
        var decks = SeparatorParser.parseInput(inputs[1], "\n\n", BingoDeck::fromInput);
        return BingoGame.builder()
            .winningDecks(playGame(decks, numbers))
            .build();
    }

    private static LinkedList<Map.Entry<BingoDeck, Integer>> playGame(List<BingoDeck> decks, List<Integer> numbers) {
        LinkedList<Map.Entry<BingoDeck, Integer>> wonBingoDecks = new LinkedList<>();
        numbers.forEach(number ->
            decks.forEach(deck -> deck.draw(number, (bingoDeck, magicNumber) -> wonBingoDecks.add(Map.entry(bingoDeck, magicNumber))))
        );
        return wonBingoDecks;
    }

    public Integer getFirstWinning() {
        return winningDecks.peek().getValue();
    }

    public Integer getLastWinning() {
        return winningDecks.getLast().getValue();
    }
}
