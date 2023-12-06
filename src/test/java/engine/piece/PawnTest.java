package engine.piece;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PawnTest extends AbstractPieceTest {
    //region Movement tests
    @Test
    void testSimpleStep() {
        // White
        assertMoveValid("8/8/8/8/8/8/4P3/8 w - - 0 1", "e2", "e3");
        // Black
        assertMoveValid("8/4p3/8/8/8/8/8/8 w - - 0 1", "e7", "e6");
    }

    @Test
    void testDoubleStep() {
        // White
        assertMoveValid("8/8/8/8/8/8/4P3/8 w - - 0 1", "e2", "e4");
        // Black
        assertMoveValid("8/4p3/8/8/8/8/8/8 w - - 0 1", "e7", "e5");
    }

    @Test
    void testIllegalDoubleStep() {
        // Illegal due to not being on the home row

        // White
        assertMoveInvalid("8/8/8/8/8/4P3/8/8 w - - 0 1", "e3", "e5");
        // Black
        assertMoveInvalid("8/8/4p3/8/8/8/8/8 w - - 0 1", "e6", "e4");
    }

    @Test
    void testBlockedStepWhite() {
        // White
        assertMoveInvalid("8/8/8/8/8/4p3/4P3/8 w - - 0 1", "e2", "e3");
        // Black
        assertMoveInvalid("8/4p3/4P3/8/8/8/8/8 w - - 0 1", "e7", "e6");
    }

    @Test
    void testBlockedDoubleStepWhite() {
        // White
        assertMoveInvalid("8/8/8/8/8/4p3/4P3/8 w - - 0 1", "e2", "e4");
        // Black
        assertMoveInvalid("8/4p3/4P3/8/8/8/8/8 w - - 0 1", "e7", "e5");
    }

    //endregion

    //region Capture tests

    //endregion
}