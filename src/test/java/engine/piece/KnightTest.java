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
}
