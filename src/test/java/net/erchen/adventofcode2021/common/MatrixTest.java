package net.erchen.adventofcode2021.common;

import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class MatrixTest {

    @Test
    void shouldCreateFromInit() {

        var matrix = Matrix.fromInitValue(3, () -> "x");

        assertThat(matrix.toString()).isEqualTo("""
            x x x
            x x x
            x x x""");
    }


    @Test
    void shouldReturnFields() {

        var matrix = Matrix.builder().fields(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9))).build();

        assertThat(matrix.field(0, 0)).isEqualTo(1);
        assertThat(matrix.field(1, 1)).isEqualTo(5);
        assertThat(matrix.field(1, 2)).isEqualTo(8);
    }

    @Test
    void shouldReturnAllFields() {

        var matrix = Matrix.builder().fields(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9))).build();

        assertThat(matrix.allFields()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void shouldReturnAllColumn() {

        var matrix = Matrix.builder().fields(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9))).build();

        assertThat(matrix.columns()).extracting(s -> s.collect(toList())).containsExactly(List.of(1, 4, 7), List.of(2, 5, 8), List.of(3, 6, 9));
    }

    @Test
    void shouldReturnAllRows() {

        var matrix = Matrix.builder().fields(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9))).build();

        assertThat(matrix.rows()).extracting(s -> s.collect(toList())).containsExactly(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9));
    }

    @Test
    void shouldReturnRow() {

        var matrix = Matrix.builder().fields(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9))).build();

        assertThat(matrix.row(0)).containsExactly(1, 2, 3);
    }

    @Test
    void shouldReturnColumn() {

        var matrix = Matrix.builder().fields(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9))).build();

        assertThat(matrix.column(0)).containsExactly(1, 4, 7);
    }

    @Test
    void shouldReturnRowsAndColumns() {

        var matrix = Matrix.builder().fields(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9))).build();

        assertThat(matrix.rowsAndColums()).extracting(s -> s.collect(toList()))
            .containsExactly(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9), List.of(1, 4, 7), List.of(2, 5, 8), List.of(3, 6, 9));
    }

    @Test
    void shouldPrintFields() {

        var matrix = Matrix.builder().fields(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9))).build();

        assertThat(matrix.toString()).isEqualTo("""
            1 2 3
            4 5 6
            7 8 9""");
    }


}