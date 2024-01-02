package engine.temp;

import engine.Board;
import engine.Position;
import engine.piece.Piece;

public class EnPassantMove extends Move.StandardMove {
    public EnPassantMove(Position from, Position to) {
        super(from, to);
    }

    @Override
    public void move(Board board) {
        // Remove the en-passant piece
        board.remove(getCapturedPiecePosition());
        super.move(board);
    }


    private Position getCapturedPiecePosition() {
        return new Position(to.file(), from.rank());
    }
}
