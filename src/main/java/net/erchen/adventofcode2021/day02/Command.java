package net.erchen.adventofcode2021.day02;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
@RequiredArgsConstructor
public class Command {
    private final Operation operation;
    private final int argument;

    public static List<Command> parseFromInput(List<String> input) {
        return input.stream().map(Command::parseFromInput).collect(toList());
    }

    private static Command parseFromInput(String input) {
        var commandParts = input.split(" ");
        var operation = Operation.valueOf(commandParts[0]);
        var argument = Integer.parseInt(commandParts[1]);
        return new Command(operation, argument);
    }

}
