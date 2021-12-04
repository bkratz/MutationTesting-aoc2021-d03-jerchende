package net.erchen.adventofcode2021.day04;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

@Getter
public class BingoDeck {

    private final static int DECK_SIZE = 5;

    private final BingoNumber[][] numbers;
    private final int[] hits;
    private int magicNumber;

    @Builder(access = AccessLevel.PRIVATE)
    private BingoDeck(BingoNumber[][] numbers) {
        this.numbers = numbers;
        this.hits = new int[DECK_SIZE * 2];
        this.magicNumber = 0;
    }

    public static BingoDeck fromInput(String input) {

        return BingoDeck.builder()
            .numbers(Arrays.stream(input.split("\n"))
                .map(String::trim)
                .map(line -> Arrays.stream(line.split(" +")).map(n -> new BingoNumber(parseInt(n), false)).toArray(BingoNumber[]::new))
                .toArray(BingoNumber[][]::new))

            .build();
    }

    public void draw(int number, BiConsumer<BingoDeck, Integer> victoryCollector) {
        for (int x = 0; x < DECK_SIZE; x++) {
            for (int y = 0; y < DECK_SIZE; y++) {
                if (numbers[x][y].getNumber() == number) {
                    numbers[x][y].setHit(true);
                    hits[x]++;
                    hits[DECK_SIZE + y]++;
                    if (magicNumber == 0 && isWon()) {
                        magicNumber = calculateMagicNumber(number);
                        victoryCollector.accept(this, magicNumber);
                    }
                    return;
                }
            }
        }
    }

    private Stream<BingoNumber> allBingoNumbers() {
        return Arrays.stream(numbers).flatMap(Arrays::stream);
    }

    private boolean isWon() {
        return Arrays.stream(hits).anyMatch(i -> i >= DECK_SIZE);
    }

    private int calculateMagicNumber(int number) {
        return allBingoNumbers().filter(bingoNumber -> !bingoNumber.isHit()).mapToInt(BingoNumber::getNumber).sum() * number;
    }

    @Data
    @Builder
    public static class BingoNumber {
        private final int number;
        private boolean hit;
    }

}
