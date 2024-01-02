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
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "c6");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "c7");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "c8");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "b5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "a5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "d5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "e5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "f5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "g5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "h5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "c4");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "c3");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "c2");
        assertMoveValid("8/8/8/2q5/8/8/8/8 w - - 0 1", "c5", "c1");
    }

    @Test
    void testCaptureDiagonalMove() {
        assertMoveValid("8/8/3n4/8/8/6Q1/8/8 w - - 0 1", "g3", "d6");
    }

    @Test
    void testCaptureOrthogonalMove() {
        assertMoveValid("8/8/5n2/8/8/8/5Q2/8 w - - 0 1", "f2", "f6");
    }

    @Test
    void testCannotStayOnSameSquare() {
        assertMoveInvalid("8/8/5q2/8/8/8/8/8 w - - 0 1", "f6", "f6");
    }
}
