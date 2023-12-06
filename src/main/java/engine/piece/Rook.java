package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

public class Rook extends Piece {
    public Rook(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

    @Override
    public boolean isMoveValid(Board board, int fromRank, int fromFile, int toRank, int toFile) {
        return false;
    }
}
