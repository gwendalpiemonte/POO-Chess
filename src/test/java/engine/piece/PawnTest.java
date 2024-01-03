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
        assertMoveValid("8/4p3/8/8/8/8/8/8 b - - 0 1", "e7", "e6");
    }

    @Test
    void testDoubleStep() {
        // White
        assertMoveValid("8/8/8/8/8/8/4P3/8 w - - 0 1", "e2", "e4");
        // Black
        assertMoveValid("8/4p3/8/8/8/8/8/8 b - - 0 1", "e7", "e5");
    }

    @Test
    void testIllegalDoubleStep() {
        // Illegal due to not being on the home row

        // White
        assertMoveInvalid("8/8/8/8/8/4P3/8/8 w - - 0 1", "e3", "e5");
        // Black
        assertMoveInvalid("8/8/4p3/8/8/8/8/8 b - - 0 1", "e6", "e4");
    }

    @Test
    void testBlockedStepWhite() {
        // White
        assertMoveInvalid("8/8/8/8/8/4p3/4P3/8 w - - 0 1", "e2", "e3");
        // Black
        assertMoveInvalid("8/4p3/4P3/8/8/8/8/8 b - - 0 1", "e7", "e6");
    }

    @Test
    void testBlockedDoubleStepWhite() {
        // White
        assertMoveInvalid("8/8/8/8/8/4p3/4P3/8 w - - 0 1", "e2", "e4");
        // Black
        assertMoveInvalid("8/4p3/4P3/8/8/8/8/8 b - - 0 1", "e7", "e5");
    }

    @Test
    void testThatPromotionCannotBeDoneFromFar() {
        // Don't ask why this test exists
        assertMoveInvalid("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", "e2", "e8");
        assertMoveInvalid("4n3/4P3/8/8/8/8/8/8 w - - 0 1", "e7", "e8");
    }

    @Test
    void testCannotGoBack() {
        assertMoveInvalid("8/8/8/4P3/8/8/8/8 w - - 0 1", "e5", "e4");
    }

    //endregion

    //region Capture tests

    @Test
    void testCaptureDiagonal() {
        assertMoveValid("8/8/8/8/4p3/3P4/8/8 w - - 0 1", "d3", "e4");
        assertMoveValid("8/8/8/8/2p5/3P4/8/8 w - - 0 1", "d3", "c4");
    }

    //endregion

    //region En-Passant tests
    @Test
    void testWhiteEnPassant() {
        assertMoveValid("k7/8/8/4pP2/8/8/8/K7 w - e6 0 1", "f5", "e6");
        assertMoveValid("k7/8/8/4pP2/8/8/8/K7 w - e6 0 1", "f5", "f6");

        assertMoveInvalid("k7/8/8/4pP2/8/8/8/K7 w - e6 0 1", "f5", "g6");
        assertMoveInvalid("k7/8/8/4pP2/8/8/8/K7 w - e6 0 1", "f5", "e5");
    }

    @Test
    void testBlackEnPassant() {
        assertMoveValid("k7/8/8/8/4pP2/8/8/K7 b - f3 0 1", "e4", "f3");
        assertMoveValid("k7/8/8/8/4pP2/8/8/K7 b - f3 0 1", "e4", "e3");

        assertMoveInvalid("k7/8/8/8/4pP2/8/8/K7 b - f3 0 1", "e4", "g3");
        assertMoveInvalid("k7/8/8/8/4pP2/8/8/K7 b - f3 0 1", "e4", "f4");
    }

    @Test
    void testCannotStayOnSameSquare() {
        assertMoveInvalid("8/8/8/8/4P3/8/8/8 w - - 0 1", "e4", "e4");
    }

    //endregion
}