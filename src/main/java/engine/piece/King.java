package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

public class King extends Piece {
    public King(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public boolean isMoveValid(Board board, Position from, Position to) {
        return false;
    }
}
