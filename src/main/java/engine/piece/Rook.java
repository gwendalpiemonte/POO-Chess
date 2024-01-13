package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.CardinalDirection;
import engine.piece.traits.MoveListener;

import java.util.List;

public class Rook extends SlidingPiece implements MoveListener {

    private boolean hasMoved;

    public Rook(PlayerColor color) {
        super(color);
        hasMoved = false;
    }

    public Rook(Piece promotedPiece) {
        super(promotedPiece.getColor());
    }

    @Override
    public List<CardinalDirection> allowedDirections() {
        return CardinalDirection.ORTHOGONALS;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

    @Override
    public void onMove() {
        hasMoved = true;
    }
}
