package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.OrthogonalMove;

public class Rook extends Piece {

    private boolean hasMoved;

    public Rook(PlayerColor color) {
        super(color);
        hasMoved = false;
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

    public Rook(Pawn pawn) {
        super(pawn.getColor());
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

    @Override
    public boolean isMoveValid(Board board, Position from, Position to) {
       return OrthogonalMove.isValid(board, from, to);
    }
}
