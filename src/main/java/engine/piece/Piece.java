package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

public abstract class Piece {
    // We go under the assumption that there is no way a piece can change color.
    private final PlayerColor color;

    public Piece(PlayerColor color) {
        this.color = color;
    }

    public abstract PieceType getType();

    /**
     * TODO: It seems strange that we pass the "from" to an instance of a piece.
     */
    public abstract boolean isMoveValid(Board board, Position from, Position to);

    public PlayerColor getColor() {
        return color;
    }
}
