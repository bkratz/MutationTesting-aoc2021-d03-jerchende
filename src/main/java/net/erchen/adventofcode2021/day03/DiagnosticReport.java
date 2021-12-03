package net.erchen.adventofcode2021.day03;

import lombok.Builder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DiagnosticReport {

    private final List<Integer> report;
    private final int reportLength;

    @Builder
    public DiagnosticReport(List<Integer> report, int reportLength) {
        this.report = report;
        this.reportLength = reportLength;
    }

    public long calculateGammaRate() {
        return calculateRateWithRelevantBit(1);
    }

    public long calculateEpsilonRate() {
        return calculateRateWithRelevantBit(0);
    }

    public long calculatePowerConsumption() {
        return calculateGammaRate() * calculateEpsilonRate();
    }

    public long calculateOxygenGeneratorRating() {
        return calculateRatingWithRelevantBit(1);
    }

    public long calculateCo2ScrubberRating() {
        return calculateRatingWithRelevantBit(0);
    }

    public long calculateLifeSupportRating() {
        return calculateOxygenGeneratorRating() * calculateCo2ScrubberRating();
    }

    private int countPositiveBitsOnPosition(List<Integer> report, int position) {
        int positiveBits = 0;
        for (int line : report) {
            if ((line & position) > 0) positiveBits++;
        }
        return positiveBits;
    }

    private long calculateRateWithRelevantBit(int bit) {
        int result = 0;
        for (int position = 1 << reportLength - 1; position >= 1; position >>>= 1) {
            result = (result << 1) | ((countPositiveBitsOnPosition(report, position) > report.size() / 2) ? bit : (bit ^ 1));
        }
        return result;
    }

    private long calculateRatingWithRelevantBit(int bit) {
        List<Integer> filtered = new ArrayList<>(report);

        for (int position = 1 << reportLength - 1; position >= 1; position >>>= 1) {
            var neededBit = (countPositiveBitsOnPosition(filtered, position) * 2 >= filtered.size()) ? bit : (bit ^ 1);
            Queue<Integer> toRemove = new LinkedList<>();
            for (Integer i : filtered) {

                if (((neededBit == 0) && ((i & position) != 0)) || ((neededBit == 1) && (i & position) == 0)) {
                    toRemove.add(i);
                }
            }
            while (!toRemove.isEmpty() && filtered.size() > 1) {
                filtered.remove(toRemove.poll());
            }
        }

        return filtered.get(0);
    }
}
