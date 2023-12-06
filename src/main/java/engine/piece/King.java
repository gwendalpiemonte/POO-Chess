package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

public class King extends Piece {
    public King(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public boolean isMoveValid(Board board, int fromRank, int fromFile, int toRank, int toFile) {
        return false;
    }
}
