package net.erchen.adventofcode2021.day19;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class BeaconScannerTest {

    @SneakyThrows
    static String sampleInput() {
        return Files.readString(Path.of("src/test/resources/day19/sample.txt"));
    }

    @SneakyThrows
    static String solutionInput() {
        return Files.readString(Path.of("src/test/resources/day19/input.txt"));
    }

    @Test
    void shouldParseInput() {

        var beaconScanner = BeaconScanner.fromInput(sampleInput(), 12);

        assertThat(beaconScanner.getScanners()).hasSize(5);
        assertThat(beaconScanner.getScanners().get(0).getName()).isEqualTo("--- scanner 0 ---");
        assertThat(beaconScanner.getScanners().get(0).getPoints()).hasSize(25);
        assertThat(beaconScanner.getScanners().get(0).getPoints().get(0)).isEqualTo((BeaconScanner.Point.builder().x(404).y(-588).z(-901).build()));
    }

    @Test
    void shouldReturnAllOrientations() {

        var beaconScanner = BeaconScanner.fromInput(sampleInput(), 12);

        assertThat(beaconScanner.getScanners().get(0).allOrientations()).hasSize(24);
    }

    @Test
    void shouldCalculateMap() {

        var beaconScanner = BeaconScanner.fromInput("""
                --- scanner 0 ---
                0,2,0
                4,1,0
                3,3,0
                                
                --- scanner 1 ---
                -1,-1,0
                -5,0,0
                -2,1,0
                4,5,0""", 3);

        beaconScanner.calculateMap();

        assertThat(beaconScanner.getMap()).containsExactlyInAnyOrder(
                BeaconScanner.Point.builder().x(0).y(2).z(0).build(),
                BeaconScanner.Point.builder().x(4).y(1).z(0).build(),
                BeaconScanner.Point.builder().x(3).y(3).z(0).build(),
                BeaconScanner.Point.builder().x(9).y(7).z(0).build()
        );
    }

    @Test
    void shouldCalculateWithDifferentOrientationMap() {

        var beaconScanner = BeaconScanner.fromInput("""
                --- scanner 0 ---
                0,2,0
                4,1,0
                3,3,0
                                
                --- scanner 1 ---
                -1,-1,0
                0,-5,0
                1,-2,0
                4,5,0""", 3);

        beaconScanner.calculateMap();

        assertThat(beaconScanner.getMap()).containsExactlyInAnyOrder(
                BeaconScanner.Point.builder().x(0).y(2).z(0).build(),
                BeaconScanner.Point.builder().x(4).y(1).z(0).build(),
                BeaconScanner.Point.builder().x(3).y(3).z(0).build(),
                BeaconScanner.Point.builder().x(10).y(6).z(0).build()
        );
    }

    @Test
    void sample() {

        var beaconScanner = BeaconScanner.fromInput(sampleInput(), 12);

        beaconScanner.calculateMap();

        assertThat(beaconScanner.getMap()).hasSize(79);
        assertThat(beaconScanner.calculateOceanSize()).isEqualTo(3621);
    }

    @Test
    void solution() {

        var beaconScanner = BeaconScanner.fromInput(solutionInput(), 12);

        beaconScanner.calculateMap();

        assertThat(beaconScanner.getMap()).hasSize(459);
        assertThat(beaconScanner.calculateOceanSize()).isEqualTo(19130);
    }

}