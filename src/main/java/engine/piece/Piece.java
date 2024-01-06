package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.bitboard.Bitboard;
import engine.move.Move;

public abstract class Piece implements Cloneable {
    // We go under the assumption that there is no way a piece can change color.
    private final PlayerColor color;

    public Piece(PlayerColor color) {
        this.color = color;
    }

    public abstract PieceType getType();

    /**
     * TODO: It seems strange that we pass the "from" to an instance of a piece.
     */
    public abstract Move getMoveFor(Board board, Position from, Position to);

    public Move getPseudoLegalMove(Board board, Position from, Position to) {
        return getMoveFor(board, from, to);
    }

    public PlayerColor getColor() {
        return color;
    }

    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
