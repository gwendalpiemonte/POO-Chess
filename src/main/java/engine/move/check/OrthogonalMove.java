package engine.move.check;

import engine.Board;
import engine.Position;
import engine.bitboard.Bitboard;
import engine.move.Move;

public class OrthogonalMove {
    public static boolean isValid(Board board, Position from, Position to) {
        Bitboard bitboard = new Bitboard();

        for (int rank = from.rank() + 1; rank < 8; rank++) {
            if (board.isOccupied(from.file(), rank)) {
                bitboard.set(new Position(from.file(), rank), true);
                break;
            }
            bitboard.set(new Position(from.file(), rank), true);
        }

        for (int rank = from.rank() - 1; rank >= 0; rank--) {
            if (board.isOccupied(from.file(), rank)) {
                bitboard.set(new Position(from.file(), rank), true);
                break;
            }
            bitboard.set(new Position(from.file(), rank), true);
        }

        for (int file = from.file() + 1; file < 8; file++) {
            if (board.isOccupied(file, from.rank())) {
                bitboard.set(new Position(file, from.rank()), true);
                break;
            }
            bitboard.set(new Position(file, from.rank()), true);
        }

        for (int file = from.file() - 1; file >= 0; file--) {
            if (board.isOccupied(file, from.rank())) {
                bitboard.set(new Position(file, from.rank()), true);
                break;
            }
            bitboard.set(new Position(file, from.rank()), true);
        }

        return !bitboard.and(Bitboard.single(to)).isEmpty();
    }
}
