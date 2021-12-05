package net.erchen.adventofcode2021.common;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

@Builder(access = AccessLevel.PACKAGE)
public class Matrix<T> {

    private final List<List<T>> fields;

    private static <T> Matrix<T> fromStreams(Stream<Stream<T>> fields) {
        return Matrix.<T>builder()
            .fields(new LinkedList<>(fields.map(s -> new LinkedList<>(s.collect(toList()))).collect(toList())))
            .build();
    }

    public static <T> Matrix<T> fromInitValue(int dimension, Supplier<T> initValue) {
        return fromStreams(Stream.generate(() -> Stream.generate(initValue).limit(dimension)).limit(dimension));
    }

    public static <T> Matrix<T> fromInput(String input, String horizontalSplitter, String vertialSplitter,
                                          Function<? super String, T> objectCreator) {
        return fromStreams(Arrays.stream(input.split(horizontalSplitter))
            .map(String::trim)
            .map(line -> Arrays.stream(line.split(vertialSplitter)).map(objectCreator)));
    }

    public T field(int x, int y) {
        return fields.get(y).get(x);
    }

    public Stream<T> allFields() {
        return fields.stream().flatMap(List::stream);
    }

    public Stream<T> row(int index) {
        return fields.get(index).stream();
    }

    public Stream<Stream<T>> rows() {
        return fields.stream().map(List::stream);
    }

    public Stream<T> column(int index) {
        return fields.stream().map(a -> a.get(index));
    }

    public Stream<Stream<T>> columns() {
        return IntStream.range(0, fields.size()).boxed().map(this::column);
    }

    public Stream<Stream<T>> rowsAndColums() {
        return Stream.concat(rows(), columns());
    }

    @Override
    public String toString() {
        return this.rows().map(line -> line.map(Object::toString).collect(joining(" "))).collect(joining("\n"));
    }

}
