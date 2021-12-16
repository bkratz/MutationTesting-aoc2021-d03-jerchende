package net.erchen.adventofcode2021.day16;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.joining;

public class HexDecoder {

    public static int sumVersions(String hexInput) {
        return decodeHexMessages(hexInput).stream().mapToInt(Message::sumVersion).sum();
    }

    static List<Message> decodeHexMessages(String hexInput) {
        return decodeBinMessages(hexToBinary(hexInput), new AtomicInteger(0), Integer.MAX_VALUE);
    }

    static List<Message> decodeBinMessages(String binInput, AtomicInteger pointer, int maxPackages) {
        var items = new LinkedList<Message>();

        while (items.size() < maxPackages && pointer.intValue() < binInput.length() - 7) {
            var version = parseVersion(binInput.substring(pointer.intValue()));
            var type = parseType(binInput.substring(pointer.intValue()));

            items.add(switch (type) {
                case 4 -> parseLiteral(version, binInput, pointer);
                default -> parseOperator(version, type, binInput, pointer);
            });
        }

        return items;
    }

    private static Literal parseLiteral(int version, String binInput, AtomicInteger globalPointer) {
        int pointer = globalPointer.intValue() + 6;
        String value = "";
        boolean last = false;
        while (!last) {
            last = binInput.charAt(pointer) == '0';
            value += binInput.substring(pointer + 1, pointer + 5);
            pointer += 5;
        }

        globalPointer.set(pointer);

        return Literal.builder()
            .version(version)
            .value(parseLong(value, 2))
            .build();
    }

    private static Operator parseOperator(int version, int type, String binInput, AtomicInteger globalPointer) {
        int pointer = globalPointer.intValue() + 6;

        var operatorBuilder = Operator.builder().type(type).version(version);

        if (binInput.charAt(pointer) == '1') {
            var children = parseInt(binInput.substring(pointer + 1, pointer + 12), 2);
            globalPointer.addAndGet(6 + 12);
            operatorBuilder.subPackages(decodeBinMessages(binInput, globalPointer, children));
        } else {
            var length = parseInt(binInput.substring(pointer + 1, pointer + 16), 2);
            operatorBuilder.subPackages(decodeBinMessages(binInput.substring(pointer + 16, pointer + 16 + length), new AtomicInteger(0), MAX_VALUE));
            globalPointer.addAndGet(6 + 16 + length);
        }
        return operatorBuilder.build();
    }

    public static String hexToBinary(String input) {
        return input.trim().chars().mapToObj(c -> switch (c) {
            case '0' -> "0000";
            case '1' -> "0001";
            case '2' -> "0010";
            case '3' -> "0011";
            case '4' -> "0100";
            case '5' -> "0101";
            case '6' -> "0110";
            case '7' -> "0111";
            case '8' -> "1000";
            case '9' -> "1001";
            case 'A' -> "1010";
            case 'B' -> "1011";
            case 'C' -> "1100";
            case 'D' -> "1101";
            case 'E' -> "1110";
            case 'F' -> "1111";
            default -> throw new IllegalStateException("Unexpected value: " + c);
        }).collect(joining());
    }

    private static int parseVersion(String input) {
        return parseInt(input.substring(0, 3), 2);
    }

    private static int parseType(String input) {
        return parseInt(input.substring(3, 6), 2);
    }

    public interface Message {
        int sumVersion();

        long calculateValue();
    }

    @Data
    @Builder
    public static class Literal implements Message {
        private final long value;
        private final int version;

        public int sumVersion() {
            return getVersion();
        }

        public long calculateValue() {
            return getValue();
        }

    }

    @Data
    @Builder
    public static class Operator implements Message {
        private final int version;
        private final int type;
        private List<Message> subPackages;

        public int sumVersion() {
            return getVersion() + subPackages.stream().mapToInt(Message::sumVersion).sum();
        }

        public long calculateValue() {
            return switch (type) {
                case 0 -> subPackages.stream().mapToLong(Message::calculateValue).sum();
                case 1 -> subPackages.stream().mapToLong(Message::calculateValue).reduce(1L, (a, b) -> a * b);
                case 2 -> subPackages.stream().mapToLong(Message::calculateValue).min().orElse(0);
                case 3 -> subPackages.stream().mapToLong(Message::calculateValue).max().orElse(0);
                case 5 -> subPackages.get(0).calculateValue() > subPackages.get(1).calculateValue() ? 1 : 0;
                case 6 -> subPackages.get(0).calculateValue() < subPackages.get(1).calculateValue() ? 1 : 0;
                case 7 -> subPackages.get(0).calculateValue() == subPackages.get(1).calculateValue() ? 1 : 0;
                default -> throw new IllegalStateException("Unexpected type: " + type);
            };
        }
    }
}

