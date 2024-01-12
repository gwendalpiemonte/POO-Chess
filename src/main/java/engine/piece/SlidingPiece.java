package engine.piece;

import chess.PlayerColor;
import engine.Board;
import engine.CardinalDirection;
import engine.Position;
import engine.bitboard.Bitboard;

import java.util.List;

public abstract class SlidingPiece extends Piece {

    public SlidingPiece(PlayerColor color) {
        super(color);
    }

    public abstract List<CardinalDirection> allowedDirections();

    @Override
    public Bitboard getMoves(Board board, Position from) {
        return allowedDirections().stream()
                // Get the bitboard for each direction...
                .map(direction -> getBitboardForDirection(board, from, direction))
                // ... and OR all the resulting bitboards together
                .reduce(new Bitboard(), Bitboard::or);
    }

    public Bitboard getBitboardForDirection(Board board, Position startPosition, CardinalDirection direction) {
        Bitboard bitboard = new Bitboard();

        // Ignore the piece itself.
        Position beginPosition = direction.apply(startPosition);
        for (Position position = beginPosition; position.isWithinBounds(); position = direction.apply(position)) {
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
