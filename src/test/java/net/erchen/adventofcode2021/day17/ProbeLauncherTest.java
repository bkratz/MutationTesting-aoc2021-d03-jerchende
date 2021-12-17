package net.erchen.adventofcode2021.day17;

import net.erchen.adventofcode2021.day17.ProbeLauncher.ProbeResult;
import org.junit.jupiter.api.Test;

import static net.erchen.adventofcode2021.day17.ProbeLauncher.Status.MATCHED;
import static net.erchen.adventofcode2021.day17.ProbeLauncher.Status.PAST_X;
import static org.assertj.core.api.Assertions.assertThat;

class ProbeLauncherTest {

    public static final ProbeLauncher sampleProbleLauncher = ProbeLauncher.builder().minX(20).maxX(30).minY(-10).maxY(-5).build();
    public static final ProbeLauncher solutionProbeLauncher = ProbeLauncher.builder().minX(79).maxX(137).minY(-176).maxY(-117).build();

    @Test
    void shouldLaunchProbe() {
        var probeLauncher = sampleProbleLauncher;

        assertThat(probeLauncher.launchProbe(7, 2)).isEqualTo(new ProbeResult(28, -7, 3, MATCHED));
        assertThat(probeLauncher.launchProbe(6, 3)).isEqualTo(new ProbeResult(21, -9, 6, MATCHED));
        assertThat(probeLauncher.launchProbe(9, 0)).isEqualTo(new ProbeResult(30, -6, 0, MATCHED));
        assertThat(probeLauncher.launchProbe(6, 9)).isEqualTo(new ProbeResult(21, -10, 45, MATCHED));
        assertThat(probeLauncher.launchProbe(17, -4)).isEqualTo(new ProbeResult(33, -9, 0, PAST_X));
    }

    @Test
    void shouldFindHighestLaunchVelocity_Sample() {
        assertThat(sampleProbleLauncher.findHighestProbe()).isEqualTo(new ProbeResult(21, -10, 45, MATCHED));
    }

    @Test
    void shouldFindHighestLaunchVelocity_Solution() {
        assertThat(solutionProbeLauncher.findHighestProbe()).isEqualTo(new ProbeResult(91, -176, 15400, MATCHED));
    }

    @Test
    void shouldFindAllProbes_Sample() {
        assertThat(sampleProbleLauncher.findAllProbes()).hasSize(112);
    }

    @Test
    void shouldFindAllProbes_Solution() {
        assertThat(solutionProbeLauncher.findAllProbes()).hasSize(5844);
    }
}