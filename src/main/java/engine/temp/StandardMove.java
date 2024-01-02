package engine.temp;

import engine.Board;
import engine.Position;
import engine.piece.Piece;

import java.util.function.Consumer;

public class StandardMove implements Move {
    protected final Position from;
    protected final Position to;
    private final Consumer<Board> additionalActions;

    public StandardMove(Position from, Position to) {
        this.from = from;
        this.to = to;
        additionalActions = null;
    }

    public StandardMove(Position from, Position to, Consumer<Board> additionalActions) {
        this.from = from;
        this.to = to;
        this.additionalActions = additionalActions;
    }

    public void move(Board board) {
        // no-op
        if (board.at(to) != null) {
            // Insert here if you want to handle the capture.
            board.remove(to.rank(), to.file());
        }

        Piece piece = board.at(from);
        board.remove(from);

        board.put(piece, to.file(), to.rank());

        if (additionalActions != null) {
            additionalActions.accept(board);
        }
    }
}
