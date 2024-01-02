package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.EnPassantMove;
import engine.move.Move;
import engine.move.PromotionMove;

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
    public Move getMoveFor(Board board, Position from, Position to) {
        // cannot move more than 2 files
        int fileDiff = from.file() - to.file();
        int fileDistance = abs(fileDiff);
        int distance = to.rank() - from.rank();
        int absDistance = abs(distance);

        // You cannot move more than 2 files apart
        if (fileDistance > 1
            || !isDirectionValid(from, to)
            || (fileDistance == 1 && !isCaptureMove(from, to))
        ) {
            return Move.illegal();
        }


        if (isCaptureMove(from, to)) {
            // Check if there is a piece on the target position
            if (board.at(to) != null) {
                return Move.standard(from, to);
            }

            // Check for en passant
            Piece enPassantPiece = board.at(to.file(), from.rank());

            if (enPassantPiece != null && board.isEnPassantCandidate(enPassantPiece)) {
                return new EnPassantMove(from, to);
            } else {
                return Move.illegal();
            }
        }

        boolean isTargetSquareFree = board.at(from.file(), from.rank() + distance) == null;

        // TODO: Add the promotion check.

        if (shouldPromote(to)) {
            return new PromotionMove(from, to);
        }

        if (isDoubleAdvance(from, to)) {
            boolean isIntermediarySquareFree = board.at(from.file(), from.rank() + distance / 2) == null;
            if (isTargetSquareFree && isIntermediarySquareFree && !this.isDeveloped(from)) {
                return Move.standard(from, to, b -> b.setEnPassantCandidate(this));
            }
        } else if (absDistance == 1 && isTargetSquareFree) {
            return Move.standard(from, to);
        }

        return Move.illegal();
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

    private boolean shouldPromote(Position position) {
        return switch (getColor()) {
            case WHITE -> position.rank() == 7;
            case BLACK -> position.rank() == 0;
        };
    }
}
