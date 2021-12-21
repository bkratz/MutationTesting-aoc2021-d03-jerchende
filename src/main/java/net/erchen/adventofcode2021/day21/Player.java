package net.erchen.adventofcode2021.day21;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Player {
    private int points;
    private int position;

    public int move(int diceValue) {
        position = (position + diceValue - 1) % 10 + 1;
        points += position;
        return points;
    }
}
