package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

public class Pawn extends Piece {
    public Pawn(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public boolean isMoveValid(Board board, Position from, Position to) {
        if (from.rank() != to.rank()) {
            // TODO: Check captures

            return false;
        }

        int distance = from.file() - to.file();
        if (distance > 0 && getColor() == PlayerColor.WHITE) {
            return false;
        }
        if (distance < 0 && getColor() == PlayerColor.BLACK) {
            return false;
        }

        int absDistance = Math.abs(distance);

        if (absDistance == 2) {
            // TODO: Check in front, 2 squares
            return !this.isDeveloped(from);
        } else if (absDistance == 1) {
            // TODO: Check in front, 1 square
            return true;
        }

        return false;
    }

    private boolean isDeveloped(Position from) {
        return switch (getColor()) {
            case BLACK -> from.rank() == 6;
            case WHITE -> from.rank() == 1;
        };
    }
}
