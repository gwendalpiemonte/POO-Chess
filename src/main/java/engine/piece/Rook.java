package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.OrthogonalMove;
import engine.temp.Move;

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
    public Move getMoveFor(Board board, Position from, Position to) {
       boolean isMoveValid = OrthogonalMove.isValid(board, from, to);

       if (!isMoveValid) {
           return Move.illegal();
       }

       return Move.standard(from, to, b -> setHasMoved());
    }
}
