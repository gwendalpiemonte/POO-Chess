package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.DiagonalMove;

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
    public boolean isMoveValid(Board board, Position from, Position to) {
        return DiagonalMove.isValid(board, from, to);
    }
}
