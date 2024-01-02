package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

public class Rook extends Piece {
    public Rook(PlayerColor color) {
        super(color);
    }

    public Rook(Pawn pawn) {
        super(pawn.getColor());
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }

    @Override
    public boolean isMoveValid(Board board, Position from, Position to) {
        boolean areRanksDifferent = from.rank() != to.rank();
        boolean areFilesDifferent = from.file() != to.file();

        if (areRanksDifferent && areFilesDifferent) {
            return false;
        }

        // TODO: There doesn't seem to be an easy way to extract these sections into a separate method

        if (areRanksDifferent) {
            int delta =  to.rank() - from.rank() > 0 ? 1 : -1;

            for (int rank = from.rank() + delta; rank != to.rank(); rank += delta) {
                if (board.at(to.file(), rank) != null) {
                    return false;
                }
            }
        }

        if (areFilesDifferent) {
            int delta = to.file() - from.file() > 0 ? 1 : -1;

            for (int file = from.file() + delta; file != to.file(); file += delta) {
                if (board.at(file, to.rank()) != null) {
                    return false;
                }
            }
        }

        return true;
    }
}
