package net.erchen.adventofcode2021.common;

import lombok.AccessLevel;
import lombok.Builder;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@Builder(access = AccessLevel.PACKAGE)
public class Matrix<T> {

    private final List<List<T>> fields;

    public static <T> Matrix<T> fromInitValue(int dimension, Supplier<T> initValue) {
        return Matrix.<T>builder()
            .fields(new LinkedList<>(
                Stream.generate(() -> new LinkedList<>(Stream.generate(initValue).limit(dimension).toList()))
                    .limit(dimension)
                    .collect(Collectors.toList())))
            .build();

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
