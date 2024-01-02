package engine.piece;

import org.junit.jupiter.api.Test;

public class QueenTest extends AbstractPieceTest {
    @Test
    void testDiagonalMove() {
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "g7");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "h8");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "g5");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "h4");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "e5");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "d4");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "c3");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "b2");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "a1");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "e7");
        assertMoveValid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "d8");
    }

    @Test
    void testOrthogonalMoves() {
        // TODO
    }

    @Test
    void testCannotStayOnSameSquare() {
        assertMoveInvalid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "f6");
    }
}
