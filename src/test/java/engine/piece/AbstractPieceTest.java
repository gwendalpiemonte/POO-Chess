package engine.piece;

import engine.Board;
import engine.Position;
import engine.temp.Move;
import engine.utils.CoordinateUtils;
import engine.utils.FenUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractPieceTest {

    public void assertMoveValid(String boardDef, String piece, String target) {
        assertThat(isMoveValid(boardDef, piece, target))
                .isTrue();
    }

    public void assertMoveInvalid(String boardDef, String piece, String target) {
        assertThat(isMoveValid(boardDef, piece, target))
                .isFalse();
    }


    public boolean isMoveValid(String boardDef, String piece, String target) {
        Board board = FenUtils.load(boardDef);

        Position piecePos = CoordinateUtils.fromString(piece);
        Position targetPos = CoordinateUtils.fromString(target);

        return !(board.at(piecePos.file(), piecePos.rank())
                .getMoveFor(board, piecePos, targetPos) instanceof Move.IllegalMove);
    }
}
