package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.DiagonalMove;
import engine.move.OrthogonalMove;

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
    public boolean isMoveValid(Board board, Position from, Position to) {
        return DiagonalMove.isValid(board, from, to) || OrthogonalMove.isValid(board, from, to);
    }
}
