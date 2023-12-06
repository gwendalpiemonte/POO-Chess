package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;

public abstract class Piece {
    // We go under the assumption that there is no way a piece can change color.
    private final PlayerColor color;

    /**
     * TODO: This will probably only be used by the King, Rook and Pawn classes, should we try to factorize this ?
     */
    private boolean developed = true;

    public Piece(PlayerColor color) {
        this.color = color;
    }

    public abstract PieceType getType();

    public abstract boolean isMoveValid(Board board, int fromRank, int fromFile, int toRank, int toFile);

    public PlayerColor getColor() {
        return color;
    }

    public boolean isDeveloped() {
        return developed;
    }

    public void setDeveloped(boolean developed) {
        this.developed = developed;
    }
}
