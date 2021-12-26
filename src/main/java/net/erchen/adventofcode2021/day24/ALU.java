package net.erchen.adventofcode2021.day24;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.joining;

@Slf4j
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ALU {

    private final List<Instruction> instructions;

    public static ALU parseFromInput(List<String> input) {
        return new ALU(input.stream()
                .filter(line -> !line.startsWith("#"))
                .filter(line -> !line.isBlank())
                .map(Instruction::parseFromInput)
                .toList());
    }

    public Memory runProgramm(int[] input) {
        var variables = new Memory();
        var inputPointer = new AtomicInteger(0);
        for (Instruction instruction : instructions) {
            var success = instruction.execute(variables, input, inputPointer);
            if (!success) {
                return null;
            }
        }
        return variables;
    }

    public LongSummaryStatistics allowedModelNumbers() {
        Integer[] incrementPerStep = new Integer[14];
        Integer[] requiredPerStep = new Integer[14];

        Instruction incrementStepIdenifier = Instruction.parseFromInput("div z 1");

        for (int i = 0; i < 14; i++) {
            if (instructions.get(i * 18 + 4).equals(incrementStepIdenifier)) {
                incrementPerStep[i] = ((Value) instructions.get(i * 18 + 15).varB).value;
            } else {
                requiredPerStep[i] = -1 * ((Value) instructions.get(i * 18 + 5).varB).value;
            }
        }

        return LongStream.range(1_111_111, 9_999_999 + 1)
                .parallel()
                .filter(l -> (!String.valueOf(l).contains("0")))
                .mapToObj(ALU::toDigitArray)
                .flatMapToLong(variableDigits -> {
                    var result = new int[14];
                    var variableDigitsIndex = 0;
                    var z = 0;
                    for (int i = 0; i < incrementPerStep.length; i++) {
                        if (incrementPerStep[i] != null) {
                            z = z * 26 + variableDigits[variableDigitsIndex] + incrementPerStep[i];
                            result[i] = variableDigits[variableDigitsIndex++];
                        } else if (requiredPerStep[i] != null) {
                            result[i] = (z % 26) - requiredPerStep[i];
                            z = Math.floorDiv(z, 26);
                            if (result[i] <= 0 || result[i] > 9) {
                                return LongStream.empty();
                            }
                        }
                    }
                    return LongStream.of(fromDigitArray(result));
                })
                .peek(number -> log.info("Found a valid model number: " + number))
                .summaryStatistics();
    }

    private static long fromDigitArray(int[] input) {
        return Long.parseLong(Arrays.stream(input).mapToObj(String::valueOf).collect(joining()));
    }

    private static int[] toDigitArray(long input) {
        return String.valueOf(input).chars().map(Character::getNumericValue).toArray();
    }

    public record Instruction(Operation operation, Variable varA, ValueHolder varB) {

        private static Instruction parseFromInput(String input) {
            var inputs = input.split(" ");
            var operation = Operation.valueOf(inputs[0]);
            var a = Variable.valueOf(inputs[1]);
            var b = inputs.length == 3 ? ValueHolder.parseFromInput(inputs[2]) : null;
            return new Instruction(operation, a, b);
        }

        public boolean execute(Memory memory, int[] input, AtomicInteger inputPointer) {
            switch (operation) {
            case inp -> memory.setValue(varA, input[inputPointer.getAndIncrement()]);
            case add -> memory.setValue(varA, memory.getValue(varA) + varB.value(memory));
            case mul -> memory.setValue(varA, memory.getValue(varA) * varB.value(memory));
            case div -> {
                if (varB.value(memory) == 0) {
                    return false;
                }
                memory.setValue(varA, Math.floorDiv(memory.getValue(varA), varB.value(memory)));
            }
            case mod -> {
                if (varA.value(memory) < 0 || varB.value(memory) <= 0) {
                    return false;
                }
                memory.setValue(varA, memory.getValue(varA) % varB.value(memory));
            }
            case eql -> memory.setValue(varA, varA.value(memory) == varB.value(memory) ? 1 : 0);
            }
            return true;
        }
    }

    @Getter
    public static class Memory {
        private final int[] memory = new int[Variable.values().length];

        public int getValue(Variable variable) {
            return memory[variable.ordinal()];
        }

        public void setValue(Variable variable, int value) {
            memory[variable.ordinal()] = value;
        }
    }

    public enum Operation {
        inp, add, mul, div, mod, eql
    }

    public interface ValueHolder {
        int value(Memory memory);

        private static ValueHolder parseFromInput(String input) {
            return switch (input) {
                case "w", "x", "y", "z" -> Variable.valueOf(input);
                default -> new Value(Integer.parseInt(input));
            };
        }
    }

    public record Value(int value) implements ValueHolder {
        public int value(Memory memory) {
            return value();
        }
    }

    public enum Variable implements ValueHolder {
        w, x, y, z;

        public int value(Memory memory) {
            return memory.getValue(this);
        }
    }
}
