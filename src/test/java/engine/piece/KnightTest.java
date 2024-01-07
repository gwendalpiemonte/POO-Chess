package engine.piece;

import org.junit.jupiter.api.Test;



public class KnightTest extends AbstractPieceTest {
    @Test
    void testPositions() {
        assertMoveValid("8/8/8/3N4/8/8/8/8 w - - 0 1", "d5", "e7");
        assertMoveValid("8/8/8/3N4/8/8/8/8 w - - 0 1", "d5", "f6");
        assertMoveValid("8/8/8/3N4/8/8/8/8 w - - 0 1", "d5", "f4");
        assertMoveValid("8/8/8/3N4/8/8/8/8 w - - 0 1", "d5", "e3");
        assertMoveValid("8/8/8/3N4/8/8/8/8 w - - 0 1", "d5", "c3");
        assertMoveValid("8/8/8/3N4/8/8/8/8 w - - 0 1", "d5", "b4");
        assertMoveValid("8/8/8/3N4/8/8/8/8 w - - 0 1", "d5", "b6");
    }

    @Test
    void testCannotStayOnSameSquare() {
        assertMoveInvalid("8/8/8/3N4/8/8/8/8 w - - 0 1", "d5", "d5");
    }

    @Test
    void testCapture() {
        assertMoveValid("8/2p5/8/3N4/8/8/8/8 w - - 0 1", "d5", "c7");
        assertMoveValid("8/4p3/8/3N4/8/8/8/8 w - - 0 1", "d5", "e7");
        assertMoveValid("8/8/5p2/3N4/8/8/8/8 w - - 0 1", "d5", "f6");
        assertMoveValid("8/8/8/3N4/5p2/8/8/8 w - - 0 1", "d5", "f4");
        assertMoveValid("8/8/8/3N4/8/4p3/8/8 w - - 0 1", "d5", "e3");
        assertMoveValid("8/8/8/3N4/8/2p5/8/8 w - - 0 1", "d5", "c3");
        assertMoveValid("8/8/8/3N4/1p6/8/8/8 w - - 0 1", "d5", "b4");
        assertMoveValid("8/8/1p6/3N4/8/8/8/8 w - - 0 1", "d5", "b6");
    }

    @Test
    void cannotCaptureAlly() {
        assertMoveInvalid("k7/8/8/8/8/4P1P1/8/K4N2 w - - 0 1", "f1", "e3");
        assertMoveInvalid("k7/8/8/8/8/4P1P1/8/K4N2 w - - 0 1", "f1", "g3");
    }
}
