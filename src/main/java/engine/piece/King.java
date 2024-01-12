package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.CardinalDirection;
import engine.Position;
import engine.bitboard.Bitboard;
import engine.piece.traits.MoveListener;

import java.util.Arrays;

public class King extends Piece implements MoveListener {

    // TODO: This should be put in a super class or an interface (same with the rook)
    private boolean hasMoved;

    public King(PlayerColor color) {
        super(color);
        hasMoved = false;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public Bitboard getMoves(Board board, Position from) {
        // A king can move in all cells adjacent to his (including diagonals)
        // It can also be rephrased as:
        // A king can move a single cell in every direction, that is *not* already taken by one of its pieces
        Bitboard baseMove = Arrays.stream(CardinalDirection.values())
                .map(direction -> direction.apply(from))
                .filter(Position::isWithinBounds)
                .collect(Bitboard.collectPositions());

        // TODO: Support castle

        return excludeCellsWithAlly(board, baseMove);
    }

    @Override
    public void onMove() {
        hasMoved = true;
    }
}
