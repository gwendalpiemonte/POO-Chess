package engine;

import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CardinalDirectionTest {
    @Test
    public void testOffsets() {
        assertThat(CardinalDirection.NORTH.apply(Position.fromString("a1")))
                .isEqualTo(Position.fromString("a2"));

        assertThat(CardinalDirection.NORTH_WEST.apply(Position.fromString("d4")))
                .isEqualTo(Position.fromString("c5"));

        assertThat(CardinalDirection.NORTH_EAST.apply(Position.fromString("d4")))
                .isEqualTo(Position.fromString("e5"));

        assertThat(CardinalDirection.EAST.apply(Position.fromString("d4")))
                .isEqualTo(Position.fromString("e4"));

        assertThat(CardinalDirection.SOUTH_EAST.apply(Position.fromString("d4")))
                .isEqualTo(Position.fromString("e3"));

        assertThat(CardinalDirection.SOUTH.apply(Position.fromString("d4")))
                .isEqualTo(Position.fromString("d3"));

        assertThat(CardinalDirection.SOUTH_WEST.apply(Position.fromString("d4")))
                .isEqualTo(Position.fromString("c3"));

        assertThat(CardinalDirection.WEST.apply(Position.fromString("d4")))
                .isEqualTo(Position.fromString("c4"));
    }

    @Test
    public void testOutOfBounds() {
        assertThat(CardinalDirection.EAST.apply(Position.fromString("h4")))
                .matches(Predicate.not(Position::isWithinBounds));

        assertThat(CardinalDirection.NORTH_EAST.apply(Position.fromString("h8")))
                .matches(Predicate.not(Position::isWithinBounds));
    }

}