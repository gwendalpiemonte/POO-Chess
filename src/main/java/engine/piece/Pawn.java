package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.bitboard.Bitboard;
import engine.piece.traits.PromotablePiece;

import java.util.List;

import static java.lang.Math.abs;

public class Pawn extends Piece implements PromotablePiece {
    public Pawn(PlayerColor color) {
        super(color);
    }

    public Pawn(Piece promotedPiece) {
        super(promotedPiece.getColor());
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }

    @Override
    public Bitboard getMoves(Board board, Position from) {
        Bitboard self = Bitboard.single(from);

        // Bitboard for simple advance
        Bitboard advance = self.shift(getShift())
                .and(board.getOccupationBoard().not());

        if (!isDeveloped(from)) {
            // We shift the simple advance board to ensure that if it can't move one cell, it cannot move two.
            advance = advance.or(advance.shift(getShift()));
        }

        return advance
                .or(getCaptures(board, from));
    }

    @Override
    public Bitboard getCaptures(Board board, Position from) {
        Bitboard self = Bitboard.single(from);
        return self.shift(getShift() - 1).or(self.shift(getShift() + 1))
                // Can only move on a capture case if an opponent is there
                .and(getCaptureBoard(board));
    }

    private Bitboard getCaptureBoard(Board board) {
        Bitboard opponentOccupation = board.getPlayerOccupation(getOpponentColor());

        return opponentOccupation
                .or(getEnPassantBoard(board));
    }

    private Bitboard getEnPassantBoard(Board board) {
        engine.Move lastMove = board.getLastMove();
        if (lastMove == null) {
            return new Bitboard();
        }

        Piece lastMovedPiece = board.at(lastMove.to());

        if (lastMovedPiece instanceof Pawn && isDoubleAdvance(lastMove.from(), lastMove.to())) {
            return Bitboard.single(lastMove.to()).shift(getShift());
        } else {
            return new Bitboard();
        }
    }

    private int getShift() {
        return switch (getColor()) {
            case BLACK -> -8;
            case WHITE -> +8;
        };
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

    @Override
    public List<PieceType> getPromotionChoices() {
        return List.of(PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN);
    }

    @Override
    public boolean shouldPromote(Position position) {
        return switch (getColor()) {
            case WHITE -> position.rank() == 7;
            case BLACK -> position.rank() == 0;
        };
    }
}
