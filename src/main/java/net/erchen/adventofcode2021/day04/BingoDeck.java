package net.erchen.adventofcode2021.day04;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import net.erchen.adventofcode2021.common.Matrix;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static java.lang.Integer.parseInt;

@Getter
public class BingoDeck {

    private final static int DECK_SIZE = 5;

    private final Matrix<BingoNumber> numbers;
    private final int[] hits;
    private int magicNumber;

    @Builder(access = AccessLevel.PRIVATE)
    private BingoDeck(Matrix<BingoNumber> numbers) {
        this.numbers = numbers;
        this.hits = new int[DECK_SIZE * 2];
        this.magicNumber = 0;
    }

    public static BingoDeck fromInput(String input) {

        return BingoDeck.builder()
            .numbers(Matrix.fromInput(input, "\n", " +", n -> new BingoNumber(parseInt(n), false)))
            .build();
    }

    public void draw(int number, BiConsumer<BingoDeck, Integer> victoryCollector) {
        for (int x = 0; x < DECK_SIZE; x++) {
            for (int y = 0; y < DECK_SIZE; y++) {
                if (numbers.fieldValue(x, y).getNumber() == number) {
                    numbers.fieldValue(x, y).setHit(true);
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


    private boolean isWon() {
        return Arrays.stream(hits).anyMatch(i -> i >= DECK_SIZE);
    }

    private int calculateMagicNumber(int number) {
        return numbers.allFieldValues().filter(bingoNumber -> !bingoNumber.isHit()).mapToInt(BingoNumber::getNumber).sum() * number;
    }

    @Data
    @Builder
    public static class BingoNumber {
        private final int number;
        private boolean hit;
    }

}
