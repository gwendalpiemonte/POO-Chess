package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.CardinalDirection;
import engine.Move;
import engine.Position;
import engine.bitboard.Bitboard;
import engine.piece.traits.HasSpecialMove;
import engine.piece.traits.MoveListener;

import java.util.Arrays;

public class King extends Piece implements MoveListener, HasSpecialMove {
    private enum CastleSide {
        KING_SIDE(+1, 2),
        QUEEN_SIDE(-1, 3);

        private final int baseOffset;
        private final int cells;

        CastleSide(int baseOffset, int cells) {
            this.baseOffset = baseOffset;
            this.cells = cells;
        }

        public int getBaseOffset() {
            return baseOffset;
        }

        public int getCells() {
            return cells;
        }

        public Position getRookPosition(Position kingPosition) {
            return kingPosition.add(baseOffset * (cells + 1));
        }

        public Position getTargetRookPosition(Position kingPosition) {
            return getRookPosition(kingPosition).add(-baseOffset * cells);
        }
    }

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

        return excludeCellsWithAlly(board, baseMove);
    }

    private Bitboard getCastleBitboard(Board board, Position from) {
        return Arrays.stream(CastleSide.values())
                .map(side -> getCastleBitboardForSide(board, from, side))
                .collect(Bitboard.collect());
    }

    private Bitboard getCastleBitboardForSide(Board board, Position from, CastleSide side) {
        Piece rookPiece = board.at(side.getRookPosition(from));
        boolean rookHasNotMoved = rookPiece instanceof Rook rook && !rook.getHasMoved();

        boolean hasVisibility = getVisibilityBitboard(side, from)
                .and(board.getOccupationBoard())
                .isEmpty();

        boolean doesNotPassACheck = getKingMoveBitboard(side, from)
                .and(board.getAttackedSquares(getColor()))
                .isEmpty();

        if (rookHasNotMoved && hasVisibility && doesNotPassACheck) {
            // You have to click on where the king would end to do a castle.
            return Bitboard.single(from).shift(side.getBaseOffset() * 2);
        } else {
            return new Bitboard();
        }
    }

    private Bitboard getVisibilityBitboard(CastleSide side, Position kingPosition) {
        return Bitboard.single(kingPosition).shift(side.getBaseOffset())
                .cumulativeShift(side.getBaseOffset(), side.getCells() - 1);
    }

    private Bitboard getKingMoveBitboard(CastleSide side, Position kingPosition) {
        // We keep the king position to also ensure that we validate that the king is not currently in check.
        return Bitboard.single(kingPosition)
                .cumulativeShift(side.getBaseOffset(), 2);
    }

    @Override
    public void onMove() {
        hasMoved = true;
    }

    @Override
    public Bitboard getSpecialMoves(Board board, Position from) {
        if (!hasMoved) {
            // We can theoretically do the castle, just check if the entire row is available
            return getCastleBitboard(board, from);
        } else {
            return new Bitboard();
        }
    }

    @Override
    public void applySpecialMove(Board board, Move move) {
        // Remove the king at from.
        board.put(board.at(move.from()), move.to());
        board.remove(move.from());

        // Move the rook
        CastleSide side = getSideFromMove(move);
        board.put(board.at(side.getRookPosition(move.from())), side.getTargetRookPosition(move.from()));
        board.remove(side.getRookPosition(move.from()));
    }

    private CastleSide getSideFromMove(Move move) {
        if (move.from().file() - move.to().file() > 0) {
            return CastleSide.QUEEN_SIDE;
        } else {
            return CastleSide.KING_SIDE;
        }
    }
}
