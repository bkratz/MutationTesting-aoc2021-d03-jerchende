package net.erchen.adventofcode2021.day12;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CaveNavigatorTest {

    @SneakyThrows
    static List<String> sampleInput1() {
        return Files.readAllLines(Path.of("src/test/resources/day12/sample1.txt")).stream().toList();
    }

    @SneakyThrows
    static List<String> sampleInput2() {
        return Files.readAllLines(Path.of("src/test/resources/day12/sample2.txt")).stream().toList();
    }

    @SneakyThrows
    static List<String> sampleInput3() {
        return Files.readAllLines(Path.of("src/test/resources/day12/sample3.txt")).stream().toList();
    }

    @SneakyThrows
    static List<String> solutionInput() {
        return Files.readAllLines(Path.of("src/test/resources/day12/input.txt")).stream().toList();
    }

    @Test
    void shouldGetAllPaths_Sample1() {
        var caveNavigator = new CaveNavigator(sampleInput1(), false);

        assertThat(caveNavigator.calculatePaths()).hasSize(10);
    }

    @Test
    void shouldGetConnections() {
        var caveNavigator = new CaveNavigator(sampleInput1(), false);

        assertThat(caveNavigator.next("start")).containsExactlyInAnyOrder("A", "b");
        assertThat(caveNavigator.next("b")).containsExactlyInAnyOrder("start", "A", "d", "end");
    }

    @Test
    void shouldGetAllPaths_Sample2() {
        var caveNavigator = new CaveNavigator(sampleInput2(), false);

        assertThat(caveNavigator.calculatePaths()).hasSize(19);
    }

    @Test
    void shouldGetAllPaths_Sample3() {
        var caveNavigator = new CaveNavigator(sampleInput3(), false);

        assertThat(caveNavigator.calculatePaths()).hasSize(226);
    }

    @Test
    void shouldGetAllPaths_Solution() {
        var caveNavigator = new CaveNavigator(solutionInput(), false);

        assertThat(caveNavigator.calculatePaths()).hasSize(3713);
    }

    @Test
    void shouldGetAllPaths_Sample1_Part2() {
        var caveNavigator = new CaveNavigator(sampleInput1(), true);

        assertThat(caveNavigator.calculatePaths()).hasSize(36);
    }

    @Test
    void shouldGetAllPaths_Sample2_Part2() {
        var caveNavigator = new CaveNavigator(sampleInput2(), true);

        assertThat(caveNavigator.calculatePaths()).hasSize(103);
    }

    @Test
    void shouldGetAllPaths_Sample3_Part2() {
        var caveNavigator = new CaveNavigator(sampleInput3(), true);

        assertThat(caveNavigator.calculatePaths()).hasSize(3509);
    }

    @Test
    void shouldGetAllPaths_Solution_Part2() {
        var caveNavigator = new CaveNavigator(solutionInput(), true);

        assertThat(caveNavigator.calculatePaths()).hasSize(91292);
    }

    @Test
    void isAllowedPath() {
        assertThat(CaveNavigator.isAllowedPath("e")).isTrue();
        assertThat(CaveNavigator.isAllowedPath("a-b-c-d-e")).isTrue();
        assertThat(CaveNavigator.isAllowedPath("a-b-c-d-e-e")).isTrue();
        assertThat(CaveNavigator.isAllowedPath("a-a-b-c-d-e-e")).isFalse();
        assertThat(CaveNavigator.isAllowedPath("start-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c-A-c")).isFalse();
    }

}