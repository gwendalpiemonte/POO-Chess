package engine;

import chess.PieceType;
import chess.PlayerColor;

public abstract class Piece {
    // We go under the assumption that there is no way a piece can change color.
    private final PlayerColor color;

    public Piece(PlayerColor color) {
        this.color = color;
    }

    public abstract PieceType getType();

    public abstract boolean isValidMove(int col, int row);

    public PlayerColor getColor() {
        return color;
    }
}
