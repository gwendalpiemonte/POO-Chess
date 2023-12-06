package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

public class Bishop extends Piece {
    public Bishop(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }

    @Override
    public boolean isMoveValid(Board board,  Position from, Position to) {
        return false;
    }
}
