package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Rook extends Piece {
    public Rook(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

    @Override
    public boolean isMoveValid(int col, int row) {
        return false;
    }
}
