package net.erchen.adventofcode2021.day16;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class HexDecoderTest {

    @SneakyThrows
    static String solutionInput() {
        return Files.readString(Path.of("src/test/resources/day16/input.txt"));
    }

    @Test
    void shouldConvertToBinary() {
        assertThat(HexDecoder.hexToBinary("D2FE28")).isEqualTo("110100101111111000101000");
        assertThat(HexDecoder.hexToBinary("38006F45291200")).isEqualTo("00111000000000000110111101000101001010010001001000000000");
    }

    @Test
    void shouldDecodeLiteral() {
        var messages = HexDecoder.decodeHexMessages("D2FE28");

        assertThat(messages).containsExactly(HexDecoder.Literal.builder().version(6).value(2021).build());
    }

    @Test
    void shouldDecodeMultipleLiteral() {
        var messages = HexDecoder.decodeBinMessages("110100010100101001000100100", new AtomicInteger(0), 5);

        assertThat(messages).containsExactly(
            HexDecoder.Literal.builder().version(6).value(10).build(),
            HexDecoder.Literal.builder().version(2).value(20).build());
    }

    @Test
    void shouldDecodeOperatorWithLengthTypeId0() {
        var messages = HexDecoder.decodeHexMessages("38006F45291200");

        assertThat(messages).containsExactly(
            HexDecoder.Operator.builder().version(1).type(6).subPackages(List.of(
                HexDecoder.Literal.builder().version(6).value(10).build(),
                HexDecoder.Literal.builder().version(2).value(20).build()
            )).build());
    }

    @Test
    void shouldDecodeOperatorWithLengthTypeId1() {
        var messages = HexDecoder.decodeHexMessages("EE00D40C823060");

        assertThat(messages).containsExactly(
            HexDecoder.Operator.builder().version(7).type(3).subPackages(List.of(
                HexDecoder.Literal.builder().version(2).value(1).build(),
                HexDecoder.Literal.builder().version(4).value(2).build(),
                HexDecoder.Literal.builder().version(1).value(3).build()
            )).build());
    }

    @Test
    void shouldSumVersions() {
        assertThat(HexDecoder.sumVersions("8A004A801A8002F478")).isEqualTo(16);
        assertThat(HexDecoder.sumVersions("620080001611562C8802118E34")).isEqualTo(12);
        assertThat(HexDecoder.sumVersions("C0015000016115A2E0802F182340")).isEqualTo(23);
        assertThat(HexDecoder.sumVersions("A0016C880162017C3686B18A3D4780")).isEqualTo(31);
    }

    @Test
    void shouldSumVersions_Solution() {
        assertThat(HexDecoder.sumVersions(solutionInput())).isEqualTo(904);
    }

    @Test
    void shouldCalculateValueType1() {
        assertThat(HexDecoder.decodeHexMessages("C200B40A82").get(0).calculateValue()).isEqualTo(3);
        assertThat(HexDecoder.decodeHexMessages("04005AC33890").get(0).calculateValue()).isEqualTo(54);
        assertThat(HexDecoder.decodeHexMessages("880086C3E88112").get(0).calculateValue()).isEqualTo(7);
        assertThat(HexDecoder.decodeHexMessages("CE00C43D881120").get(0).calculateValue()).isEqualTo(9);
        assertThat(HexDecoder.decodeHexMessages("D8005AC2A8F0").get(0).calculateValue()).isEqualTo(1);
        assertThat(HexDecoder.decodeHexMessages("F600BC2D8F").get(0).calculateValue()).isEqualTo(0);
        assertThat(HexDecoder.decodeHexMessages("9C005AC2F8F0").get(0).calculateValue()).isEqualTo(0);
        assertThat(HexDecoder.decodeHexMessages("9C0141080250320F1802104A08").get(0).calculateValue()).isEqualTo(1);
    }

    @Test
    void shouldCalculateValue_Solution() {
        assertThat(HexDecoder.decodeHexMessages(solutionInput()).get(0).calculateValue()).isEqualTo(200476472872L);
    }
}