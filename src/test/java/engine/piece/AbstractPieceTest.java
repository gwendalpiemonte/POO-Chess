package engine.piece;

import engine.Board;
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

        int pieceFile = CoordinateUtils.charCoordinateToIndex(piece.charAt(0));
        int pieceRank = CoordinateUtils.intCoordinateToIndex(piece.charAt(1));

        int targetFile = CoordinateUtils.charCoordinateToIndex(target.charAt(0));
        int targetRank = CoordinateUtils.intCoordinateToIndex(target.charAt(1));

        return board.at(pieceFile, pieceRank)
                .isMoveValid(board, pieceFile, pieceRank, targetFile, targetRank);
    }
}
