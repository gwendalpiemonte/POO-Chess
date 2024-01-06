package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.CastlingMove;
import engine.move.Move;

public class King extends Piece {

    // TODO: This should be put in a super class or an interface (same with the rook)
    private boolean hasMoved;

    public King(PlayerColor color) {
        super(color);
        hasMoved = false;
    }


    /**
     * Marks the piece has having moved
     */
    public void setHasMoved() {
        hasMoved = true;
    }

    /**
     * Resets the moved status of the piece.
     * Only useful for the FENUtils class
     */
    public void resetHasMoved() {
        hasMoved = false;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    private Move getMove(Position from, Position to) {
        int rankDistance = from.rank() - to.rank();
        int fileDistance = from.file() - to.file();

        if (rankDistance == 0 && fileDistance == 0) {
            return Move.illegal();
        }

        if (Math.abs(rankDistance) > 1 || Math.abs(fileDistance) > 1) {
            return Move.illegal();
        }

        return Move.standard(from, to, b -> setHasMoved());
    }

    @Override
    public Move getPseudoLegalMove(Board board, Position from, Position to) {
        return getMove(from, to);
    }

    @Override
    public Move getMoveFor(Board board, Position from, Position to) {
        Move pseudoLegalCheck = getMove(from, to);

        int fileDistance = from.file() - to.file();

        boolean isKingSideCastle = fileDistance == -2;
        int rookFile = isKingSideCastle ? 7 : 0;
        if (!hasMoved && Math.abs(fileDistance) == 2 && board.at(rookFile, getColor() == PlayerColor.WHITE ? 0 : 7) instanceof Rook rook && !rook.getHasMoved()) {
            // Check if pieces are on the way
            int direction = isKingSideCastle ? 1 : -1;
            for (int file = from.file() + direction; file != rookFile - direction; file += direction) {
                Position pos = new Position(file, from.rank());
                if (board.at(pos) != null || !board.getAttackersForPosition(getColor(), pos).isEmpty()) {
                    return Move.illegal();
                }
            }

            return new CastlingMove(from, to, direction);
        }

        if (!board.getAttackersForPosition(getColor(), to).isEmpty()) {
            // We cannot move here, as there is someone that attacks this cell
            return Move.illegal();
        }

        return pseudoLegalCheck;
    }
}
