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
    void testCannotStayOnSameSquare() {
        assertMoveInvalid("8/8/8/3K4/8/8/8/8 w - - 0 1", "d5", "d5");
    }
}
