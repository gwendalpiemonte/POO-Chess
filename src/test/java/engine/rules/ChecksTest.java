package engine.rules;

import engine.piece.AbstractPieceTest;
import org.junit.jupiter.api.Test;

/**
 * Testing class for a lot of cases related to the check situation in the chess rules.
 */
public class ChecksTest extends AbstractPieceTest {
    @Test
    void testPieceCannotMoveWhenChecked() {
        assertMoveInvalid("3k4/8/8/3r4/8/8/8/3KB3 w - - 0 1", "e1", "f2");
    }

    @Test
    void testPieceCanBlockAttacker() {
        assertMoveValid("3k4/8/8/3r4/8/8/8/3KB3 w - - 0 1", "e1", "d2");
    }

    @Test
    void testPieceCanCaptureAttacker() {
        assertMoveValid("3k4/8/8/3r4/8/8/8/3KB2B w - - 0 1", "h1", "d5");
    }

    @Test
    void testPieceCannotCaptureAttackerButStillChecked() {
        assertMoveValid("3k4/8/8/3r4/8/8/8/3KB2B w - - 0 1", "h1", "d5");
    }

    @Test
    void testPieceCannotCaptureWhenChecked() {
        assertMoveInvalid("8/8/3k4/8/3pq3/3K4/8/8 w - - 0 1", "d4", "f5");
    }

    @Test
    void testPieceCannotMoveThatWouldCauseCheck() {
        assertMoveInvalid("k2r4/8/8/8/3N4/8/3K4/8 w - - 0 1", "d4",  "f5");
    }

    @Test
    void testCannotCaptureThatWouldCauseCheck() {
        assertMoveInvalid("k2q4/8/8/3N4/5b2/8/8/3K4 w - - 0 1", "d5", "f4");
    }
}
