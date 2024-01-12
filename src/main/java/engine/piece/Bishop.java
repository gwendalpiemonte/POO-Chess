package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.CardinalDirection;

import java.util.List;

public class Bishop extends SlidingPiece {
    public Bishop(PlayerColor color) {
        super(color);
    }

    public Bishop(Piece promotedPiece) {
        super(promotedPiece.getColor());
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }

    @Override
    public List<CardinalDirection> allowedDirections() {
        return CardinalDirection.DIAGONALS;
    }
}
