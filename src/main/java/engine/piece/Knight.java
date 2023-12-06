package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;

public class Knight extends Piece {
    public Knight(PlayerColor color) {
        super(color);
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }

    @Override
    public boolean isMoveValid(Board board, Position from, Position to) {
        int rankDistance = to.rank() - from.rank();
        int fileDistance = to.file() - from.file();

        int absRankDistance = Math.abs(rankDistance);
        int absFileDistance = Math.abs(fileDistance);

        return (absRankDistance == 2 && absFileDistance == 1) || (absRankDistance == 1 && absFileDistance == 2);
    }
}
