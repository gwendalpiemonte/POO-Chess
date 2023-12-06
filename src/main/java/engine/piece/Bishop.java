package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

public class Bishop extends Piece {
    public Bishop(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }

    @Override
    public boolean isMoveValid(Board board, int fromRank, int fromFile, int toRank, int toFile) {
        return false;
    }
}
