package net.erchen.adventofcode2021.day04;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BingoDeckTest {

    @Test
    void shouldParseInput() {
        var bingoDeck = BingoDeck.fromInput("""
            22 13 17 11  0
             8  2 23  4 24
            21  9 14 16  7
             6 10  3 18  5
             1 12 20 15 19
            """);

        assertThat(bingoDeck.getNumbers()).hasDimensions(5, 5);
        assertThat(bingoDeck.getNumbers()[0]).extracting(BingoDeck.BingoNumber::getNumber).containsExactly(22, 13, 17, 11, 0);
        assertThat(bingoDeck.getNumbers()[1]).extracting(BingoDeck.BingoNumber::getNumber).containsExactly(8, 2, 23, 4, 24);
        assertThat(bingoDeck.getNumbers()[2]).extracting(BingoDeck.BingoNumber::getNumber).containsExactly(21, 9, 14, 16, 7);
        assertThat(bingoDeck.getNumbers()[3]).extracting(BingoDeck.BingoNumber::getNumber).containsExactly(6, 10, 3, 18, 5);
        assertThat(bingoDeck.getNumbers()[4]).extracting(BingoDeck.BingoNumber::getNumber).containsExactly(1, 12, 20, 15, 19);
    }

}