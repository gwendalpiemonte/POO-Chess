package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.CardinalDirection;

import java.util.ArrayList;
import java.util.List;

public class Queen extends SlidingPiece {
    public Queen(PlayerColor color) {
        super(color);
    }

    public Queen(Piece promotedPiece) {
        super(promotedPiece.getColor());
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public List<CardinalDirection> allowedDirections() {
        List<CardinalDirection> directions = new ArrayList<>(CardinalDirection.DIAGONALS);
        directions.addAll(CardinalDirection.ORTHOGONALS);

        return directions;
    }
}
