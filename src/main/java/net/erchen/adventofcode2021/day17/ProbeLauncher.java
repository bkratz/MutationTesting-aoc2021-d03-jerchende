package net.erchen.adventofcode2021.day17;

import lombok.Builder;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.compare;
import static java.lang.Math.abs;
import static java.lang.Math.max;

@Builder
public class ProbeLauncher {

    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;

    public ProbeResult launchProbe(int forward, int upward) {
        int x = 0, y = 0, highest = 0;
        while (true) {
            x += forward;
            y += upward;
            highest = max(highest, y);
            forward += compare(0, forward);
            upward -= 1;

            if (x > maxX) {
                return new ProbeResult(x, y, highest, Status.PAST_X);
            }
            if (y < minY) {
                return new ProbeResult(x, y, highest, Status.PAST_Y);
            }

            if (x >= minX && y <= maxY) {
                return new ProbeResult(x, y, highest, Status.MATCHED);
            }
        }
    }

    public List<ProbeResult> findAllProbes() {
        List<ProbeResult> probeResults = new LinkedList<>();

        for (int forward = 0; forward <= maxX; forward++) {
            for (int upward = minY; upward <= abs(minY); upward++) {
                var probeResult = launchProbe(forward, upward);

                if (probeResult.status == Status.MATCHED) {
                    probeResults.add(probeResult);
                }

                if (probeResult.status == Status.PAST_X) {
                    break;
                }
            }
        }
        return probeResults;
    }

    public ProbeResult findHighestProbe() {
        return findAllProbes().stream().max(Comparator.comparingInt(ProbeResult::highest)).orElseThrow();
    }

    public record ProbeResult(int x, int y, int highest, Status status) {
    }

    public enum Status {
        MATCHED, PAST_X, PAST_Y
    }


}
