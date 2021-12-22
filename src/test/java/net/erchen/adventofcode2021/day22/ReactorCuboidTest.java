package net.erchen.adventofcode2021.day22;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class ReactorCuboidTest {

    @Test
    void shouldCreateCubeFromInput() {
        var cuboid = Reactor.Cuboid.fromInput("on x=-10..12,y=-13..-11,z=14..17");

        assertThat(cuboid.x1()).isEqualTo(-10);
        assertThat(cuboid.x2()).isEqualTo(13); // exclusive
        assertThat(cuboid.y1()).isEqualTo(-13);
        assertThat(cuboid.y2()).isEqualTo(-10); // exclusive
        assertThat(cuboid.z1()).isEqualTo(14);
        assertThat(cuboid.z2()).isEqualTo(18); // exclusive
        assertThat(cuboid.state()).isTrue();
    }

    @Test
    void shouldGetWidth() {
        var cuboid = Reactor.Cuboid.fromInput("on x=-10..12,y=-13..-11,z=14..17");

        assertThat(cuboid.width()).isEqualTo(23);
    }

    @Test
    void shouldGetHeight() {
        var cuboid = Reactor.Cuboid.fromInput("on x=-10..12,y=-13..-11,z=14..17");

        assertThat(cuboid.height()).isEqualTo(3);
    }

    @Test
    void shouldGetDepth() {
        var cuboid = Reactor.Cuboid.fromInput("on x=-10..12,y=-13..-11,z=14..17");

        assertThat(cuboid.depth()).isEqualTo(4);
    }

    @Test
    void shouldVolume() {
        var cuboid = Reactor.Cuboid.fromInput("on x=10..12,y=10..12,z=10..12");

        assertThat(cuboid.volume()).isEqualTo(27);
    }

    @ParameterizedTest
    @CsvSource(delimiterString = "#", value = {
            "on x=10..20,y=30..40,z=50..60#true",
            "on x=12..18,y=32..38,z=52..58#true",
            "on x=10..10,y=30..30,z=50..50#true",
            "on x=20..20,y=40..40,z=60..60#true",
            "on x=5..20,y=30..40,z=50..60#false",
            "on x=10..20,y=25..40,z=50..60#false",
            "on x=10..20,y=30..40,z=45..60#false",
            "on x=2..2,y=2..2,z=2..2#false"
    })
    void shouldFullyContains(String otherStr, boolean isFullyEnclosed) {
        var base = Reactor.Cuboid.fromInput("on x=10..20,y=30..40,z=50..60");
        var other = Reactor.Cuboid.fromInput(otherStr);

        assertThat(base.fullyContains(other)).isEqualTo(isFullyEnclosed);
    }

    @ParameterizedTest
    @CsvSource(delimiterString = "#", value = {
            "on x=10..20,y=30..40,z=50..60#true",
            "on x=12..18,y=32..38,z=52..58#true",
            "on x=10..10,y=30..30,z=50..50#true",
            "on x=20..20,y=40..40,z=60..60#true",
            "on x=5..20,y=30..40,z=50..60#true",
            "on x=10..20,y=25..40,z=50..60#true",
            "on x=10..20,y=30..40,z=45..60#true",
            "on x=20..20,y=30..40,z=50..60#true",
            "on x=10..20,y=30..30,z=50..60#true",
            "on x=10..20,y=30..40,z=50..50#true",
            "on x=2..2,y=2..2,z=2..2#false"
    })
    void shouldOverlap(String otherStr, boolean isOverlaped) {
        var base = Reactor.Cuboid.fromInput("on x=10..20,y=30..40,z=50..60");
        var other = Reactor.Cuboid.fromInput(otherStr);

        assertThat(base.overLaps(other)).isEqualTo(isOverlaped);
    }

    @Test
    void shouldNotSplitIfNotOverlapping() {
        var base = Reactor.Cuboid.fromInput("on x=10..20,y=30..40,z=50..60");
        var splitter = Reactor.Cuboid.fromInput("on x=5..5,y=5..5,z=5..5");

        var splits = base.split(splitter);
        assertThat(splits).containsExactly(base);
    }

    @Test
    void shouldSplitOnX1() {
        var base = Reactor.Cuboid.fromInput("on x=10..20,y=30..40,z=50..60");
        var splitter = Reactor.Cuboid.fromInput("on x=15..25,y=30..40,z=50..60");

        var splits = base.split(splitter);
        assertThat(splits).containsExactly(
                Reactor.Cuboid.fromInput("on x=10..14,y=30..40,z=50..60"),
                Reactor.Cuboid.fromInput("on x=15..20,y=30..40,z=50..60")
        );
    }

    @Test
    void shouldSplitOnX2() {
        var base = Reactor.Cuboid.fromInput("on x=10..20,y=30..40,z=50..60");
        var splitter = Reactor.Cuboid.fromInput("on x=5..15,y=30..40,z=50..60");

        var splits = base.split(splitter);
        assertThat(splits).containsExactly(
                Reactor.Cuboid.fromInput("on x=10..15,y=30..40,z=50..60"),
                Reactor.Cuboid.fromInput("on x=16..20,y=30..40,z=50..60")
        );
    }

    @Test
    void shouldSplitOnAllAxis() {
        var base = Reactor.Cuboid.fromInput("on x=0..10,y=0..10,z=0..10");
        var splitter = Reactor.Cuboid.fromInput("on x=1..9,y=1..9,z=1..9");

        var splits = base.split(splitter).toList();
        assertThat(splits).hasSize(27);
        assertThat(splits.stream().mapToLong(Reactor.Cuboid::volume).sum()).isEqualTo(base.volume());
    }

    @Test
    void shouldSplitOnOneSide() {
        var base = Reactor.Cuboid.fromInput("on x=0..10,y=0..10,z=0..10");
        var splitter = Reactor.Cuboid.fromInput("on x=1..11,y=1..11,z=1..11");

        var splits = base.split(splitter).toList();
        assertThat(splits).hasSize(8);
        assertThat(splits.stream().mapToLong(Reactor.Cuboid::volume).sum()).isEqualTo(base.volume());
    }

    @Test
    void shouldSplitInHalfs() {
        var base = Reactor.Cuboid.fromInput("on x=1..10,y=1..10,z=1..10");
        var splitter = Reactor.Cuboid.fromInput("on x=6..15,y=11..11,z=11..11");

        var splits = base.split(splitter);
        assertThat(splits).containsExactly(
                Reactor.Cuboid.fromInput("on x=1..5,y=1..10,z=1..10"),
                Reactor.Cuboid.fromInput("on x=6..10,y=1..10,z=1..10")
        );
    }

}