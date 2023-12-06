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
        if (from.file() != to.file()) {
            // TODO: Check captures

            return false;
        }

        if (!isDirectionValid(from, to)) {
            return false;
        }

        int distance = to.rank() - from.rank();
        int absDistance = Math.abs(distance);

        boolean isTargetSquareFree = board.at(from.file(), from.rank() + distance) == null;

        if (absDistance == 2) {
            boolean isIntermediarySquareFree = board.at(from.file(), from.rank() + distance / 2) == null;

            return isTargetSquareFree && isIntermediarySquareFree && !this.isDeveloped(from);
        } else if (absDistance == 1) {
            return isTargetSquareFree;
        }

        return false;
    }

    private boolean isDirectionValid(Position from, Position to) {
        return switch (getColor()) {
            case BLACK -> to.rank() < from.rank();
            case WHITE -> to.rank() > from.rank();
        };
    }

    private boolean isDeveloped(Position from) {
        return switch (getColor()) {
            case BLACK -> from.rank() != 6;
            case WHITE -> from.rank() != 1;
        };
    }
}
