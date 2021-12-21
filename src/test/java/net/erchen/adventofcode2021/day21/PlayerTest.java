package net.erchen.adventofcode2021.day21;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerTest {

    @Test
    void shouldMove() {
        var player = new Player(0, 9);

        player.move(1);
        assertThat(player.getPosition()).isEqualTo(10);
        assertThat(player.getPoints()).isEqualTo(10);

        player.move(1);
        assertThat(player.getPosition()).isEqualTo(1);
        assertThat(player.getPoints()).isEqualTo(11);
    }

}