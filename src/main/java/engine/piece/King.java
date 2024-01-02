package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

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

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }

    @Override
    public boolean isMoveValid(Board board, Position from, Position to) {
        int rankDistance = from.rank() - to.rank();
        int fileDistance = from.file() - to.file();

        if (rankDistance == 0 && fileDistance == 0) {
            return false;
        }

        // TODO: Check if the king is under attack if he moves to `to`

        boolean isKingSideCastle = fileDistance == -2;
        int rookFile = isKingSideCastle ? 7 : 0;
        if (!hasMoved && Math.abs(fileDistance) == 2 && board.at(rookFile, getColor() == PlayerColor.WHITE ? 7 : 0) instanceof Rook rook && !rook.getHasMoved()) {
            // Check if pieces are on the way
            int direction = isKingSideCastle ? 1 : -1;
            for (int file = from.file() + direction; file != rookFile - direction; file += direction) {
                if (board.at(file, from.rank()) != null) {
                    return false;
                }
            }

            // TODO: Check for checks (hehe) on the way

            return true;
        }

        return Math.abs(rankDistance) <= 1 && Math.abs(fileDistance) <= 1;
    }
}
