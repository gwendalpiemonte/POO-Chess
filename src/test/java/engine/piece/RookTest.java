package engine.piece;

import org.junit.jupiter.api.Test;

public class RookTest extends AbstractPieceTest {
    @Test
    void testSimpleSlide() {
        assertMoveValid("8/8/8/8/8/3R4/8/8 w - - 0 1", "d3", "d4");
        assertMoveValid("8/8/8/8/8/3R4/8/8 w - - 0 1", "d3", "a3");
        assertMoveValid("8/8/8/8/8/3R4/8/8 w - - 0 1", "d3", "g3");
        assertMoveValid("8/8/8/8/8/3R4/8/8 w - - 0 1", "d3", "d8");
    }

    @Test
    void testCannotSlideRankAndFileAtOnce() {
        assertMoveInvalid("8/8/8/8/8/3R4/8/8 w - - 0 1", "d3", "f6");
    }

    @Test
    void testCannotSlideTroughOtherPiece() {
        assertMoveInvalid("8/8/3p4/8/8/3R4/8/8 w - - 0 1", "d3", "d7");
    }

    @Test
    void testCannotStayOnSameSquare() {
        assertMoveInvalid("8/8/3R4/8/8/8/8/8 w - - 0 1", "d6", "d6");
    }

    @Test
    void testPieceOnTargetSquare() {
        assertMoveValid("8/8/3p4/8/3R4/8/8/8 w - - 0 1", "d4", "d6");
    }

}
