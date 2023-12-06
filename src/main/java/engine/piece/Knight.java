package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

public class Knight extends Piece {
    public Knight(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }

    @Override
    public boolean isMoveValid(Board board, Position from, Position to) {
        return false;
    }
}
