package net.erchen.adventofcode2021.day23;

import net.erchen.adventofcode2021.day23.AmphipodBurrow.Field;
import net.erchen.adventofcode2021.day23.AmphipodBurrow.State;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static net.erchen.adventofcode2021.day23.AmphipodBurrow.Amphipod.*;
import static net.erchen.adventofcode2021.day23.AmphipodBurrow.State.emptyState;
import static org.assertj.core.api.Assertions.assertThat;

class AmphipodBurrowTest {

    final AmphipodBurrow sample = new AmphipodBurrow(
            Bronze, Copper, Bronze, Desert,
            Amber, Desert, Copper, Amber
    );

    final AmphipodBurrow soulution = new AmphipodBurrow(
            Amber, Copper, Copper, Desert,
            Bronze, Desert, Amber, Bronze
    );

    final AmphipodBurrow samplePart2 = new AmphipodBurrow(
            Bronze, Copper, Bronze, Desert,
            Desert, Copper, Bronze, Amber,
            Desert, Bronze, Amber, Copper,
            Amber, Desert, Copper, Amber
    );

    final AmphipodBurrow soulutionPart2 = new AmphipodBurrow(
            Amber, Copper, Copper, Desert,
            Desert, Copper, Bronze, Amber,
            Desert, Bronze, Amber, Copper,
            Bronze, Desert, Amber, Bronze
    );

    @Test
    void shouldConstructBurrow() {
        assertThat(sample.getBurrow()).hasSize(19);
        assertThat(sample.getPositions()).hasSize(19);

        assertThat(samplePart2.getBurrow()).hasSize(27);
        assertThat(samplePart2.getPositions()).hasSize(27);
    }

    /*
     * #############
     * #...........#
     * ###B#C#B#D###
     *   #X#D#C#A#
     *   #########
     */
    @Test
    void shouldFindNoNextFieldForBlocked() {
        assertThat(sample.nextFields(sample.getHomeA().get(1), Desert, null, 0, emptyState(sample.getPositions()))).isEmpty();
    }

    /*
     * #############
     * #...#.......#
     * ###X#.#.#.###
     *   #.#.#.#.#
     *   #########
     */
    @Test
    void shouldNextFindFieldsInBlockedHallway() {
        assertThat(sample.nextFields(sample.getHomeA().get(0), Bronze, null, 0, mockState(sample.getHallway().get(3)))).containsExactlyInAnyOrder(
                new AmphipodBurrow.FieldWithCosts(sample.getHallway().get(0), 3),
                new AmphipodBurrow.FieldWithCosts(sample.getHallway().get(1), 2)
        );
    }

    /*
     * #############
     * #.......#...#
     * ###X#.#.#.###
     *   #.#.#.#.#
     *   #########
     */
    @Test
    void shouldNextFindFieldsInBlockedHallwayWithoutOtherHomes() {
        assertThat(sample.nextFields(sample.getHomeA().get(0), Bronze, null, 0, mockState(sample.getHallway().get(7)))).containsExactlyInAnyOrder(
                new AmphipodBurrow.FieldWithCosts(sample.getHallway().get(0), 3),
                new AmphipodBurrow.FieldWithCosts(sample.getHallway().get(1), 2),
                new AmphipodBurrow.FieldWithCosts(sample.getHallway().get(3), 2),
                new AmphipodBurrow.FieldWithCosts(sample.getHallway().get(5), 4),
                new AmphipodBurrow.FieldWithCosts(sample.getHomeB().get(1), 5)
        );
    }

    /*
     * #############
     * #...........#
     * ###B#C#B#D###
     *   #A#D#C#A#
     *   #########
     */
    @Test
    void shouldFindNextStates() {
        assertThat(sample.nextStates(emptyState(sample.getPositions()))).hasSize(28);
    }

    @Test
    void shouldFindNextStatesBig() {
        assertThat(samplePart2.nextStates(emptyState(samplePart2.getPositions()))).hasSize(28);
    }

    @Test
    void shouldGetFieldsBelow() {
        assertThat(sample.getHallway().get(1).allBelow()).isEmpty();
        assertThat(sample.getHomeA().get(1).allBelow()).isEmpty();
        assertThat(sample.getHomeA().get(0).allBelow()).containsExactly(sample.getHomeA().get(1));
        assertThat(sample.getHomeB().get(1).allBelow()).isEmpty();
        assertThat(sample.getHomeB().get(0).allBelow()).containsExactly(sample.getHomeB().get(1));

        assertThat(samplePart2.getHomeA().get(0).allBelow()).containsExactly(samplePart2.getHomeA().get(1), samplePart2.getHomeA().get(2), samplePart2.getHomeA().get(3));
        assertThat(samplePart2.getHomeA().get(2).allBelow()).containsExactly(samplePart2.getHomeA().get(3));
    }

    @Test
    void shouldFindMinEnergyWay_Sample() {
        assertThat(sample.findLeastTotalEnergyWay()).isEqualTo(12521);
    }

    @Test
    void shouldFindMinEnergyWay_Solution() {
        assertThat(soulution.findLeastTotalEnergyWay()).isEqualTo(13066);
    }

    @Test
    @Disabled
    void shouldFindMinEnergyWay_SamplePart2() {
        assertThat(samplePart2.findLeastTotalEnergyWay()).isEqualTo(44169);
    }

    @Test
    @Disabled
    void shouldFindMinEnergyWay_SolutionPart2() {
        assertThat(soulutionPart2.findLeastTotalEnergyWay()).isGreaterThan(41322).isEqualTo(0L);
    }

    static State mockState(Field... occupiedFields) {
        var fields = new AmphipodBurrow.Amphipod[27];
        for (Field occupiedField : occupiedFields) {
            fields[occupiedField.getPositionReference()] = Amber;
        }
        return emptyState(fields);
    }
}