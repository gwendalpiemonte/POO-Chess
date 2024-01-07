package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.check.DiagonalMove;
import engine.move.check.OrthogonalMove;
import engine.move.Move;

public class Queen extends Piece {
    public Queen(PlayerColor color) {
        super(color);
    }

    public Queen(Pawn pawn) {
        super(pawn.getColor());
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }

    @Override
    public Move getMoveFor(Board board, Position from, Position to) {
        boolean isMoveValid = DiagonalMove.isValid(board, getColor(), from, to)
                || OrthogonalMove.isValid(board, getColor(), from, to);

        if (!isMoveValid) {
            return Move.illegal();
        }

        return Move.standard(from, to);
    }
}
