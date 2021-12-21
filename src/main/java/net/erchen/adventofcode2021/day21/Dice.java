package net.erchen.adventofcode2021.day21;

import lombok.Getter;

@Getter
public class Dice {
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