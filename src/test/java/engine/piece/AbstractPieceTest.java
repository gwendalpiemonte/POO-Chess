package engine.piece;

import engine.Board;
import engine.Position;
import engine.move.Move;
import engine.utils.FenUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractPieceTest {

    public void assertMoveValid(String boardDef, String piece, String target) {
        assertThat(isMoveValid(boardDef, piece, target))
                .as("Unexpected validity of move")
                .overridingErrorMessage("The move is invalid, while it should be valid.")
                .isTrue();
    }

    public void assertMoveInvalid(String boardDef, String piece, String target) {
        assertThat(isMoveValid(boardDef, piece, target))
                .as("Unexpected validity of move")
                .overridingErrorMessage("The move is valid, while it should be invalid.")
                .isFalse();
    }

    public boolean isMoveValid(String boardDef, String piece, String target) {
        Board board = FenUtils.load(boardDef);

        Position piecePos = Position.fromString(piece);
        Position targetPos = Position.fromString(target);

        return !(board.at(piecePos.file(), piecePos.rank())
                .getMoveFor(board, piecePos, targetPos) instanceof Move.IllegalMove);
    }
}
