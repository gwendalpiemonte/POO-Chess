package engine.temp;

import engine.Board;
import engine.Position;

import java.util.function.Consumer;

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

    void move(Board board);

    default boolean isValid() {return true;}
}
