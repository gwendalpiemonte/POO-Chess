package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

public class King extends Piece {
    public King(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }


    /**
     * TODO: Check if the king is under attack if he moves to `to`
     */
    @Override
    public boolean isMoveValid(Board board, Position from, Position to) {
        int rankDistance = from.rank() - to.rank();
        int fileDistance = from.file() - to.file();

        if (rankDistance == 0 && fileDistance == 0) {
            return false;
        }

        return Math.abs(rankDistance) <= 1 && Math.abs(fileDistance) <= 1;
    }
}
