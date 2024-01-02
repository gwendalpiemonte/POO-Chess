package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.check.DiagonalMove;
import engine.move.Move;

public class Bishop extends Piece {
    public Bishop(PlayerColor color) {
        super(color);
    }

    public Bishop(Pawn pawn) {
        super(pawn.getColor());
    }

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }

    @Override
    public Move getMoveFor(Board board, Position from, Position to) {
        boolean isMoveValid = DiagonalMove.isValid(board, from, to);

        if (!isMoveValid) {
            return Move.illegal();
        }

        return Move.standard(from, to);
    }
}
