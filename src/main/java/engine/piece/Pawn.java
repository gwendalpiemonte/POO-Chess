package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

public class Pawn extends Piece {
    public Pawn(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public boolean isMoveValid(Board board, int fromRank, int fromFile, int toRank, int toFile) {
        return false;
    }
}
