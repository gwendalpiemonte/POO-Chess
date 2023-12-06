package engine.piece;

import chess.PieceType;

public class Rook extends Piece {
    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

    @Override
    public boolean isValidMove(int col, int row) {
        return false;
    }
}
