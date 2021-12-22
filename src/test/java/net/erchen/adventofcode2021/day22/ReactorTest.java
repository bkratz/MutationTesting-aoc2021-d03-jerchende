package net.erchen.adventofcode2021.day22;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReactorTest {

    @SneakyThrows
    static List<String> sampleInputPart1() {
        return Files.readAllLines(Path.of("src/test/resources/day22/samplePart1.txt")).stream().toList();
    }

    @SneakyThrows
    static List<String> solutionInputPart1() {
        return Files.readAllLines(Path.of("src/test/resources/day22/inputPart1.txt")).stream().toList();
    }

    @SneakyThrows
    static List<String> sampleInputPart2() {
        return Files.readAllLines(Path.of("src/test/resources/day22/samplePart2.txt")).stream().toList();
    }

    @SneakyThrows
    static List<String> solutionInputPart2() {
        return Files.readAllLines(Path.of("src/test/resources/day22/inputPart2.txt")).stream().toList();
    }

    @Test
    void shouldRebootWithMiniSample() {
        var reactor = new Reactor();

        reactor.rebootInstruction("on x=10..12,y=10..12,z=10..12");
        assertThat(reactor.countActiveCubes()).isEqualTo(27);

        reactor.rebootInstruction("on x=11..13,y=11..13,z=11..13");
        assertThat(reactor.countActiveCubes()).isEqualTo(46);

        reactor.rebootInstruction("off x=9..11,y=9..11,z=9..11");
        assertThat(reactor.countActiveCubes()).isEqualTo(38);

        reactor.rebootInstruction("on x=10..10,y=10..10,z=10..10");
        assertThat(reactor.countActiveCubes()).isEqualTo(39);
    }

    @Test
    void shouldRebootWithSamplePart1() {
        var reactor = new Reactor();

        reactor.rebootInstruction(sampleInputPart1());
        assertThat(reactor.countActiveCubes()).isEqualTo(590784);
    }

    @Test
    void shouldRebootWithSolutionPart1() {
        var reactor = new Reactor();

        reactor.rebootInstruction(solutionInputPart1());
        assertThat(reactor.countActiveCubes()).isEqualTo(543306);
    }

    @Test
    void shouldRebootWithSamplePart2() {
        var reactor = new Reactor();

        reactor.rebootInstruction(sampleInputPart2());
        assertThat(reactor.countActiveCubes()).isEqualTo(2758514936282235L);
    }

    @Test
    @Disabled("needs about 13 minutes... :(")
    void shouldRebootWithSolution() {
        var reactor = new Reactor();

        reactor.rebootInstruction(solutionInputPart2());
        assertThat(reactor.countActiveCubes()).isEqualTo(1285501151402480L);
    }
}
