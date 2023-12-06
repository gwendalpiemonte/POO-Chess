package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

public class Queen extends Piece {
    public Queen(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public boolean isMoveValid(Board board, Position from, Position to) {
        return false;
    }
}
