package engine.piece;

import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.bitboard.Bitboard;

import java.util.List;

public abstract class SlidingPiece extends Piece {
    public enum CardinalDirection {
        NORTH_WEST(+7),
        NORTH(+8),
        NORTH_EAST(+9),
        EAST(+1),
        SOUTH_EAST(-7),
        SOUTH(-8),
        SOUTH_WEST(-9),
        WEST(-1);


        private final int offset;

        public static final List<CardinalDirection> DIAGONALS = List.of(
                NORTH_WEST,
                NORTH_EAST,
                SOUTH_EAST,
                SOUTH_WEST
        );

        public static final List<CardinalDirection> ORTHOGONALS = List.of(
                NORTH,
                SOUTH,
                EAST,
                WEST
        );

        CardinalDirection(int offset) {
            this.offset = offset;
        }

        public int getOffset() {
            return offset;
        }
    }

    public SlidingPiece(PlayerColor color) {
        super(color);
    }

    public abstract List<CardinalDirection> allowedDirections();

    @Override
    public Bitboard getPseudoLegalMoves(Board board, Position from) {
        return allowedDirections().stream()
                // Get the bitboard for each direction...
                .map(direction -> getBitboardForDirection(board, from, direction))
                // ... and OR all the resulting bitboards together
                .reduce(new Bitboard(), Bitboard::or);
    }

    public Bitboard getBitboardForDirection(Board board, Position startPosition, CardinalDirection direction) {
        Bitboard bitboard = new Bitboard();

        for (int index = startPosition.index(); Position.isIndexWithinBounds(index); index += direction.getOffset()) {
            Position position = Position.fromIndex(index);
            Piece piece = board.at(position);

            if (piece != null) {
                if (piece.getColor() != getColor()) {
                    bitboard.set(position, true);
                }

                return bitboard;
            } else {
                // No piece at target
                bitboard.set(position, true);
            }
        }

        return bitboard;
    }
}
