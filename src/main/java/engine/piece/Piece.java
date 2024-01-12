package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.bitboard.Bitboard;

import static engine.Board.getOppositeColor;

public abstract class Piece implements Cloneable {
    // We go under the assumption that there is no way a piece can change color.
    private final PlayerColor color;

    public Piece(PlayerColor color) {
        this.color = color;
    }

    public abstract PieceType getType();

    public abstract Bitboard getMoves(Board board, Position from);

    public Bitboard getCaptures(Board board, Position from) {
        // Nearly all pieces (except the pawn) captures if there's a piece on the target.
        return getMoves(board, from)
                .and(board.getPlayerOccupation(getOpponentColor()));
    }

    public PlayerColor getColor() {
        return color;
    }

    protected PlayerColor getOpponentColor() {
        return getOppositeColor(getColor());
    }

    public Bitboard excludeCellsWithAlly(Board board, Bitboard sourceBitboard) {
        Bitboard freeOrCaptureCells = board.getPlayerOccupation(getColor()).not();

        return sourceBitboard.and(freeOrCaptureCells);
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
