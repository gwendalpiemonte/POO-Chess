package engine.move;

import engine.Board;
import engine.Position;
import engine.piece.Pawn;
import engine.promotion.PromotionChoice;

import java.util.function.Supplier;

public class PromotionMove implements Move {

    private final Position from;
    private final Position to;
    private Supplier<PromotionChoice> choiceSupplier;

    public PromotionMove(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Move addPrompt(Supplier<PromotionChoice> supplier) {
        choiceSupplier = supplier;
        return this;
    }

    @Override
    public void move(Board board) {
        board.remove(to);

        board.put(choiceSupplier.get().promote(((Pawn) board.at(from))), to);
        board.remove(from);
    }
}
