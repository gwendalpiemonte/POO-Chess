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

    public abstract Bitboard getPseudoLegalMoves(Board board, Position from);

    public PlayerColor getColor() {
        return color;
    }

    public boolean isAlreadyTaken(Board board, Position position) {
        Piece piece = board.at(position);
        return piece != null && piece.getColor() == getColor();
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
