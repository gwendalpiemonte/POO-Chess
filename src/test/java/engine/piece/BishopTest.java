package engine.piece;

import org.junit.jupiter.api.Test;

public class BishopTest extends AbstractPieceTest {
    @Test
    void testDiagonalMove() {
        assertMoveValid("8/8/8/3b4/8/8/8/8 w - - 0 1", "d5", "c6");
        assertMoveValid("8/8/8/3b4/8/8/8/8 w - - 0 1", "d5", "a8");
        assertMoveValid("8/8/8/3b4/8/8/8/8 w - - 0 1", "d5", "a2");
        assertMoveValid("8/8/8/3b4/8/8/8/8 w - - 0 1", "d5", "b3");
        assertMoveValid("8/8/8/3b4/8/8/8/8 w - - 0 1", "d5", "f3");
        assertMoveValid("8/8/8/3b4/8/8/8/8 w - - 0 1", "d5", "f7");
    }

    @Test
    void testCanOnlyMoveOnDiagonals() {
        assertMoveInvalid("8/8/8/8/3b4/8/8/8 w - - 0 1", "d4", "a8");
        assertMoveInvalid("8/8/8/8/3b4/8/8/8 w - - 0 1", "d4", "b5");
        assertMoveInvalid("8/8/8/8/3b4/8/8/8 w - - 0 1", "d4", "g4");
        assertMoveInvalid("8/8/8/8/3b4/8/8/8 w - - 0 1", "d4", "e4");
    }

    @Test
    void testCannotStayOnSameSquare() {
        assertMoveInvalid("8/8/8/8/3b4/8/8/8 w - - 0 1", "d4", "d4");
    }

    @Test
    void testCannotGoThroughOtherPiece() {
        assertMoveInvalid("8/8/8/3b4/8/5P2/8/8 w - - 0 1", "d5", "g2");
        assertMoveInvalid("8/8/8/2P5/8/4b3/8/8 w - - 0 1", "e3", "a7");
    }
}
