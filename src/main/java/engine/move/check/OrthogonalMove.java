package engine.move.check;

import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.piece.Piece;

public class OrthogonalMove {
    public static boolean isValid(Board board, PlayerColor color, Position from, Position to) {
        boolean areRanksDifferent = from.rank() != to.rank();
        boolean areFilesDifferent = from.file() != to.file();

        if (areRanksDifferent && areFilesDifferent) {
            return false;
        }

        if (!areRanksDifferent && !areFilesDifferent) {
            return false;
        }

        // TODO: There doesn't seem to be an easy way to extract these sections into a separate method

        if (areRanksDifferent) {
            int delta = to.rank() - from.rank() > 0 ? 1 : -1;

            for (int rank = from.rank() + delta; rank != to.rank(); rank += delta) {
                if (board.at(to.file(), rank) != null) {
                    return false;
                }
            }
        }

        if (areFilesDifferent) {
            int delta = to.file() - from.file() > 0 ? 1 : -1;

            for (int file = from.file() + delta; file != to.file(); file += delta) {
                if (board.at(file, to.rank()) != null) {
                    return false;
                }
            }
        }

        Piece target = board.at(to);
        return target == null || target.getColor() != color;
    }
}
