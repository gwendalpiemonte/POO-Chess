package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.bitboard.Bitboard;

import java.util.List;

public class Knight extends Piece {

    /**
     * To improve the readability of the code (you don't want to see the alternative),
     * we have hardcoded the offsets of the knight for our notation.
     */
    private final List<Integer> knightMoveOffsets = List.of(
            +10,+23,+25,+14,
            -10,-23,-25,-14
    );
    public Knight(PlayerColor color) {
        super(color);
    }

    public Knight(Piece promotedPiece) {
        super(promotedPiece.getColor());
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Bitboard getMoves(Board board, Position from) {
        Bitboard moves = knightMoveOffsets.stream()
                .map(offset -> Position.fromIndex(from.index() + offset))
                .filter(Position::isWithinBounds)
                .collect(Bitboard.collectPositions());

        return excludeCellsWithAlly(board, moves);
    }
}
