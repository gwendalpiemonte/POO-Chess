package engine.temp;

import engine.Board;
import engine.Position;

public class EnPassantMove extends StandardMove {
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
