package net.erchen.adventofcode2021.day06;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class LanternfishSimulator {

    public static long simulateLanternfishPopulation(List<Integer> startAges, int days) {
        var fishedPerDay = Stream.generate(() -> new AtomicLong(0)).limit(9).collect(toCollection(LinkedList::new));
        startAges.forEach(age -> fishedPerDay.get(age).incrementAndGet());

        for (int day = 0; day < days; day++) {
            var todayFishes = fishedPerDay.poll();
            fishedPerDay.add(todayFishes);
            fishedPerDay.get(6).addAndGet(todayFishes.longValue());
        }

        return fishedPerDay.stream().mapToLong(AtomicLong::longValue).sum();
    }

}
