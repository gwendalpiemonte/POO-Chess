package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

public class Queen extends Piece {
    public Queen(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public boolean isMoveValid(Board board, int fromRank, int fromFile, int toRank, int toFile) {
        return false;
    }
}
