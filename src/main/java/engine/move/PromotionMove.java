package engine.move;

import engine.Board;
import engine.Position;
import engine.piece.Pawn;
import engine.promotion.PromotionChoice;

public class PromotionMove implements Move {

    private final Position from;
    private final Position to;

    public PromotionMove(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void move(Board board) {
        throw new RuntimeException("Should be called with the move(Board, PieceType) overload");
    }

    public void move(Board board, PromotionChoice choice) {
        board.remove(to);

        board.put(choice.promote(((Pawn) board.at(from))), to);
        board.remove(from);
    }
}
