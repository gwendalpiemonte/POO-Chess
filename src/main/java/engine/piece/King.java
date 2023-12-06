package engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class King extends Piece {
    public King(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public boolean isMoveValid(int col, int row) {
        return false;
    }
}
