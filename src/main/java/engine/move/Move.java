package engine.move;

import engine.Board;
import engine.Position;
import engine.promotion.PromotionChoice;

import java.util.function.Consumer;
import java.util.function.Supplier;

@FunctionalInterface
public interface Move {
    class IllegalMove implements Move {
        public void move(Board board) {
            // no-op
        }

        @Override
        public boolean isValid() {
            return false;
        }
    }

    static Move illegal() {
        return new IllegalMove();
    }

    static Move standard(Position from, Position to) {
        return new StandardMove(from, to);
    }

    static Move standard(Position from, Position to, Consumer<Board> additionalActions) {
        return new StandardMove(from, to, additionalActions);
    }

    default void addPrompt(Supplier<PromotionChoice> supplier) {
        // NO-OP
    }

    void move(Board board);

    default boolean isValid() {
        return true;
    }
}
