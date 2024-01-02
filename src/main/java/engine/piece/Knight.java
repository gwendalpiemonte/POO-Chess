package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.Move;

public class Knight extends Piece {
    public Knight(PlayerColor color) {
        super(color);
    }

    public Knight(Pawn pawn) {
        super(pawn.getColor());
    }

    @Override
    public PieceType getType() {
        return PieceType.KNIGHT;
    }

    @Override
    public Move getMoveFor(Board board, Position from, Position to) {
        int rankDistance = to.rank() - from.rank();
        int fileDistance = to.file() - from.file();

        int absRankDistance = Math.abs(rankDistance);
        int absFileDistance = Math.abs(fileDistance);

        if ((absRankDistance != 2 || absFileDistance != 1) && (absRankDistance != 1 || absFileDistance != 2)) {
            return Move.illegal();
        }

        return Move.standard(from, to);
    }
}
