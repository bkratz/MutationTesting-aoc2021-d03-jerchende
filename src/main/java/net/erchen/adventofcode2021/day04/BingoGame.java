package net.erchen.adventofcode2021.day04;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import net.erchen.adventofcode2021.common.parser.SeparatorParser;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class BingoGame {

    private final List<BingoDeck> decks;
    private final List<Integer> numbers;

    public static BingoGame fromInput(String input) {
        var inputs = input.split("\n\n", 2);
        return BingoGame.builder()
            .numbers(Arrays.stream(inputs[0].split(",")).map(Integer::parseInt).collect(toList()))
            .decks(SeparatorParser.parseInput(inputs[1], "\n\n", BingoDeck::fromInput))
            .build();
    }


}
