package engine.piece;

import engine.Board;
import engine.Position;
import engine.move.Move;
import engine.utils.FenUtils;
import harness.JsonExportTestListener;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractPieceTest {
    public void assertMoveValid(String boardDef, String piece, String target, String description) {
        isMoveValid(
                boardDef,
                piece,
                target,
                description,
                true
        );
    }

    public void assertMoveValid(String boardDef, String piece, String target) {
        isMoveValid(
                boardDef,
                piece,
                target,
                "",
                true
        );
    }

    public void assertMoveInvalid(String boardDef, String piece, String target, String description) {
        isMoveValid(
                boardDef,
                piece,
                target,
                description,
                false
        );
    }

    public void assertMoveInvalid(String boardDef, String piece, String target) {
        isMoveValid(
                boardDef,
                piece,
                target,
                "",
                false
        );
    }

    public void isMoveValid(String boardDef, String piece, String target, String description, boolean expected) {
        Board board = FenUtils.load(boardDef);

        // To make some tests pass

        Position piecePos = Position.fromString(piece);
        Position targetPos = Position.fromString(target);


        Move move = board.getMoveFor(piecePos, targetPos);

        JsonExportTestListener.registerTest(boardDef, piece, target, description, expected);

        boolean actual = !(move instanceof Move.IllegalMove);

        assertThat(actual)
                .as("Unexpected validity of move")
                .isEqualTo(expected);
    }
}
