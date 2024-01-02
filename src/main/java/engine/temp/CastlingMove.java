package engine.temp;

import engine.Board;
import engine.Position;
import engine.piece.Piece;

public class CastlingMove extends StandardMove {
    private final int direction;

    public CastlingMove(Position from, Position to, int direction) {
        super(from, to);
        this.direction = direction;
    }

    @Override
    public void move(Board board) {
        // Move the king (using super)
        super.move(board);
        // Move the rook
        Position rookFrom = new Position(direction == -1 ? 0 : 7, from.rank());
        Piece rook = board.at(rookFrom);

        board.remove(rookFrom);
        board.put(rook, to.file() - direction, to.rank());
    }
}
