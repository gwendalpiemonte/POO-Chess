package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Bishop extends Piece {
    public Bishop(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }

    @Override
    public boolean isMoveValid(int col, int row) {
        return false;
    }
}
