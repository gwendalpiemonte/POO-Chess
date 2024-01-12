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
        assertMoveValid(
                boardDef,
                piece,
                target,
                ""
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
        assertMoveInvalid(
                boardDef,
                piece,
                target,
                ""
        );
    }

    public void isMoveValid(String boardDef, String piece, String target, String description, boolean expected) {
        Board board = FenUtils.load(boardDef);

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
