package net.erchen.adventofcode2021.day01;

import java.util.List;

public class SonarUtils {

    public static int countIncreases(List<Integer> report) {
        int increases = 0;
        for (int i = 1; i < report.size(); i++) {
            if (report.get(i - 1) < report.get(i)) {
                increases++;
            }
        }
        return increases;
    }

    public static int countIncreasesInBlocks(List<Integer> report) {
        int increases = 0;
        for (int i = 3; i < report.size(); i++) {
            if ((report.get(i - 3) + report.get(i - 2) + report.get(i - 1)) < (report.get(i - 2) + report.get(i - 1) + report.get(i))) {
                increases++;
            }
        }
        return increases;
    }


}
