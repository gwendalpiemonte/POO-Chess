package engine.piece.traits;

import engine.Board;
import engine.Move;
import engine.Position;
import engine.bitboard.Bitboard;

/**
 * {@code HasSpecialMove} is a trait interface that allows to specify special moves.
 * <br/>
 * "Special Moves" are moves that are not included in checks calculations,
 * and manipulate the board further than a simple move.
 */
public interface HasSpecialMove {
    /**
     * Gets a bitboard of all special moves.
     *
     * @return The bitboard of all legal special moves.
     */
    Bitboard getSpecialMoves(Board board, Position from);

    /**
     * Apply a special move on the provided board.
     *
     * @param board The board to apply the move into
     * @apiNote The implementation of this function <b>must</b> move the piece itself.
     * This allows for implementations where the piece does not move to the indicated position.
     */
    void applySpecialMove(Board board, Move move);
}
