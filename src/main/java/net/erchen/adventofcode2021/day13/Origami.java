package net.erchen.adventofcode2021.day13;

import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

@Getter
public class Origami {

    private boolean[][] fields;
    private final List<String> commands;
    private final int size;

    @Builder
    public Origami(int size, String input) {
        this.fields = new boolean[size][size];
        var inputs = input.split("\n\n");
        Arrays.stream(inputs[0].split("\n")).map(i -> i.split(",")).forEach(coordinates -> {
            fields[parseInt(coordinates[1])][parseInt(coordinates[0])] = true;
        });

        this.size = size;
        this.commands = List.of(inputs[1].split("\n"));
    }

    public void executeCommands(int limit) {
        commands.stream().limit(limit).forEach(this::executeCommand);
    }

    private void executeCommand(String command) {
        var param = parseInt(command.substring(command.indexOf('=') + 1));
        fold(param, command.startsWith("fold along x"));
    }

    private void fold(int axis, boolean foldOnXAxis) {
        boolean[][] newFields = new boolean[size][size];
        for (int i = 1; i <= axis; i++) {
            for (int z = 0; z < size; z++) {
                if (foldOnXAxis) {
                    newFields[z][axis - i] = fields[z][axis - i] || fields[z][axis + i];
                } else {
                    newFields[axis - i][z] = fields[axis - i][z] || fields[axis + i][z];
                }
            }
        }
        fields = newFields;
    }

    public int countActiveFields() {
        int count = 0;
        for (boolean[] row : fields) {
            for (boolean field : row) {
                if (field) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (boolean[] row : fields) {
            for (boolean field : row) {
                sb.append(field ? '#' : '.');
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
