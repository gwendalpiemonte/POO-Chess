package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

import static java.lang.Math.abs;

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
        // cannot move more than 2 files
        int fileDiff = from.file() - to.file();
        int fileDistance = abs(fileDiff);
        // You cannot move more than 2 files apart
        if (fileDistance > 1) {
            return false;
        }

        int distance = to.rank() - from.rank();
        int absDistance = abs(distance);

        if (!isDirectionValid(from, to)) {
            return false;
        }

        // If the file distance is 1, then the move MUST be a capture
        if (fileDistance == 1 && !isCaptureMove(from, to)) {
            return false;
        }

        if (isCaptureMove(from, to)) {
            // Check if there is a piece on the target position
            if (board.at(to) != null) {
                return true;
            }

            // Check for en passant
            Piece enPassantPiece = board.at(to.file(), from.rank());

            return enPassantPiece != null && board.isEnPassantCandidate(enPassantPiece);
        }

        boolean isTargetSquareFree = board.at(from.file(), from.rank() + distance) == null;

        if (isDoubleAdvance(from, to)) {
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

    private boolean isCaptureMove(Position from, Position to) {
        return abs(to.file() - from.file()) == 1
                && abs(to.rank() - from.rank()) == 1;
    }

    public static boolean isDoubleAdvance(Position from, Position to) {
        int distance = to.rank() - from.rank();
        int absDistance = abs(distance);

        return absDistance == 2;
    }

    private boolean isDeveloped(Position from) {
        return switch (getColor()) {
            case BLACK -> from.rank() != 6;
            case WHITE -> from.rank() != 1;
        };
    }
}
