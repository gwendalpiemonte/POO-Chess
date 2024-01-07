package engine.piece;

import org.junit.jupiter.api.Test;

public class QueenTest extends AbstractPieceTest {
    @Test
    void testDiagonalMove() {
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "g7");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "h8");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "g5");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "h4");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "e5");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "d4");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "c3");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "b2");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "a1");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "e7");
        assertMoveValid("8/8/5q2/8/8/8/8/8 b - - 0 1", "f6", "d8");
    }

    @Test
    void testOrthogonalMoves() {
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "c6");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "c7");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "c8");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "b5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "a5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "d5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "e5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "f5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "g5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "h5");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "c4");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "c3");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "c2");
        assertMoveValid("8/8/8/2q5/8/8/8/8 b - - 0 1", "c5", "c1");
    }


    @Test
    void testCannotGoThroughPieces() {
        assertMoveInvalid("8/4q3/4P3/8/8/8/4R3/8 b - - 0 1", "e7", "e2");
        assertMoveInvalid("8/8/2q5/3P4/8/5R2/8/8 b - - 0 1", "c6", "f3");
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

    @Test
    void cannotCaptureAlly() {
        assertMoveInvalid("k7/8/8/8/8/8/8/KQ6 w - - 0 1", "b1", "a1");
        assertMoveInvalid("k7/8/8/8/8/8/8/KQR5 w - - 0 1", "b1", "c1");
    }
}
