package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.bitboard.Bitboard;
import engine.move.check.OrthogonalMove;
import engine.move.Move;

import java.util.List;

public class Rook extends SlidingPiece implements MoveListener {

    private boolean hasMoved;

    public Rook(PlayerColor color) {
        super(color);
        hasMoved = false;
    }

    @Override
    public List<CardinalDirection> allowedDirections() {
        return CardinalDirection.ORTHOGONALS;
    }

    /**
     * Marks the piece has having moved
     */
    public void setHasMoved() {
        hasMoved = true;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public void resetHasMoved() {
        hasMoved = false;
    }

    public Rook(Pawn pawn) {
        super(pawn.getColor());
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
