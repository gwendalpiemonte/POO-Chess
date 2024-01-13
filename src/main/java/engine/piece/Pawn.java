package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;
import engine.bitboard.Bitboard;
import engine.piece.traits.PromotablePiece;

import java.util.List;

import static java.lang.Math.abs;

public class Pawn extends Piece implements PromotablePiece {
    public Pawn(PlayerColor color) {
        super(color);
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
        return Bitboard.single(from.add(getOffset() - 1))
                .or(Bitboard.single(from.add(getOffset() + 1)))
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

        if (lastMovedPiece instanceof Pawn && isDoubleAdvance(lastMove)) {
            return Bitboard.single(lastMove.to()).shift(getShift());
        } else {
            return new Bitboard();
        }
    }

    /**
     * Returns the bitboard shift value that represents the pawn advancement
     *
     * @return The shift amount
     */
    private int getShift() {
        return switch (getColor()) {
            case BLACK -> -8;
            case WHITE -> +8;
        };
    }

    /**
     * Returns the position offset value that represents the pawn advancement
     *
     * @return The offset amount
     */
    private int getOffset() {
        return switch (getColor()) {
            case BLACK -> -12;
            case WHITE -> +12;
        };
    }

    /**
     * Returns true if the move advances two ranks
     *
     * @param move The move to check
     * @return true if the move advances two ranks
     */
    public static boolean isDoubleAdvance(Move move) {
        int distance = move.to().rank() - move.from().rank();

        return abs(distance) == 2;
    }

    /**
     * Checks whether the pawn is developed or not (has moved from its starting rank)
     *
     * @param from The position to check
     * @return true if the position's rank is the starting rank for the given color
     */
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
