package engine.piece;

import org.junit.jupiter.api.Test;

public class KingTest extends AbstractPieceTest {
    @Test
    void testPositions() {
        assertMoveValid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "c6");
        assertMoveValid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "d6");
        assertMoveValid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "e6");
        assertMoveValid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "e5");
        assertMoveValid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "e4");
        assertMoveValid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "d4");
        assertMoveValid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "c4");
        assertMoveValid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "c5");
    }

    @Test
    void testCapture() {
        assertMoveValid("8/8/8/8/4p3/4K3/8/8 w - - 0 1", "e3", "e4");
    }

    @Test
    void testCannotStayOnSameSquare() {
        assertMoveInvalid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "d5");
    }

    @Test
    void testCannotMoveToCheck() {
        assertMoveInvalid("4k3/8/8/8/8/8/8/3R1R2 b - - 0 1", "e8", "f8");
        assertMoveInvalid("4k3/8/8/8/8/8/8/3R1R2 b - - 0 1", "e8", "d8");
        assertMoveInvalid("4k3/8/8/8/8/8/8/3R1R2 b - - 0 1", "e8", "f7");
        assertMoveInvalid("4k3/8/8/8/8/8/8/3R1R2 b - - 0 1", "e8", "d7");

        assertMoveValid("4k3/8/8/8/8/8/8/3R1R2 b - - 0 1", "e8", "e7");
    }

    // Checks that the move where the king moves to a cell protected by its current cell.
    @Test
    void testCannotMoveToCheckAfterMove() {
        assertMoveInvalid("8/4k3/8/4r3/8/8/4K3/8 w - - 0 1", "e2", "e1");
    }

    @Test
    void testCannotMoveToCheckAfterMoveWithKing() {
        assertMoveInvalid("8/8/8/8/8/k7/8/K7 w - - 0 1", "a1", "a2");
        assertMoveInvalid("8/8/8/8/8/k7/8/K7 w - - 0 1", "a1", "b2");

        assertMoveValid("8/8/8/8/8/k7/8/K7 w - - 0 1", "a1", "b1");
    }

    @Test
    void testCannotCastleWithIntermediaryCheck() {
        assertMoveInvalid("1k6/8/8/8/b7/8/8/R3K3 w Q - 0 1", "e1", "c1");
        assertMoveInvalid("1k6/8/8/8/5b2/8/8/R3K3 w Q - 0 1", "e1", "c1");
        assertMoveInvalid("1k6/8/8/8/8/8/3n4/4K2R w K - 0 1", "e1", "g1");
        assertMoveInvalid("1k6/8/8/8/8/3b4/8/4K2R w K - 0 1", "e1", "g1");
    }

    @Test
    void testKingCanCastle() {
        assertMoveValid("1k6/8/8/8/8/8/8/4K2R w K - 0 1", "e1", "g1");
        assertMoveValid("1k6/8/8/8/8/8/8/R3K3 w Q - 0 1", "e1", "c1");
    }

    @Test
    void testKingCannotCastleIfMoved() {
        assertMoveInvalid("1k6/8/8/8/8/8/8/4K2R w - - 0 1", "e1", "g1");
        assertMoveInvalid("1k6/8/8/8/8/8/8/R3K3 w - - 0 1", "e1", "c1");
    }

    @Test
    void testKingDoesCastleIfTowerIsAttacked() {
        assertMoveValid("1k6/8/8/8/4b3/8/8/R3K3 w Q - 0 1", "e1", "c1");
    }

    @Test
    void cannotCaptureAlly() {
        assertMoveInvalid("k7/8/8/8/8/8/8/KQ6 w - - 0 1", "a1", "b1");
        assertMoveInvalid("k7/8/8/8/8/8/8/1QK5 w - - 0 1", "c1", "b1");
    }

    void testCanGoInFrontOfPawn() {
        assertMoveValid("8/8/3k4/8/3P4/8/8/8 b - - 0 1", "d6", "d5");
    }
}
