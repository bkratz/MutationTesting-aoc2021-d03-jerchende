package net.erchen.adventofcode2021.day24;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static net.erchen.adventofcode2021.day24.ALU.Operation.*;
import static net.erchen.adventofcode2021.day24.ALU.Variable.*;
import static org.assertj.core.api.Assertions.assertThat;

class ALUTest {

    @SneakyThrows
    static List<String> sampleInput() {
        return Files.readAllLines(Path.of("src/test/resources/day24/sample.txt")).stream().toList();
    }

    @SneakyThrows
    static List<String> solutionInput() {
        return Files.readAllLines(Path.of("src/test/resources/day24/input.txt")).stream().toList();
    }

    @Test
    void shouldParse() {
        var alu = ALU.parseFromInput(sampleInput());

        assertThat(alu.getInstructions()).containsExactly(
                new ALU.Instruction(inp, w, null),
                new ALU.Instruction(add, z, w),
                new ALU.Instruction(mod, z, new ALU.Value(2)),
                new ALU.Instruction(div, w, new ALU.Value(2)),
                new ALU.Instruction(add, y, w),
                new ALU.Instruction(mod, y, new ALU.Value(2)),
                new ALU.Instruction(div, w, new ALU.Value(2)),
                new ALU.Instruction(add, x, w),
                new ALU.Instruction(mod, x, new ALU.Value(2)),
                new ALU.Instruction(div, w, new ALU.Value(2)),
                new ALU.Instruction(mod, w, new ALU.Value(2))
        );
    }

    @Test
    void shouldRunProgramm() {
        var alu = ALU.parseFromInput(sampleInput());
        var variables = alu.runProgramm(new int[] { 7 });

        assertThat(variables.getMemory()).containsExactly(0, 1, 1, 1);
    }

    @Test
    void shouldFailOnDivZero() {
        var alu = ALU.parseFromInput(List.of("add w 7", "div w 0"));
        var variables = alu.runProgramm(new int[] {});

        assertThat(variables).isNull();
    }

    @Test
    void shouldFailOnModZero() {
        var alu = ALU.parseFromInput(List.of("add w 7", "mod w 0"));
        var variables = alu.runProgramm(new int[] {});

        assertThat(variables).isNull();
    }

    @Test
    void shouldFailOnNegativeMod() {
        var alu = ALU.parseFromInput(List.of("add w -7", "mod w 2"));
        var variables = alu.runProgramm(new int[] {});

        assertThat(variables).isNull();
    }

    @Test
    void shouldGetModelNumbers() {
        var alu = ALU.parseFromInput(solutionInput());
        var modelNumber = alu.allowedModelNumbers();

        assertThat(modelNumber.getMax()).isEqualTo(92793949489995L);
        assertThat(modelNumber.getMin()).isEqualTo(51131616112781L);
    }
}