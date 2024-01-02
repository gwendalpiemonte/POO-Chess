package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.OrthogonalMove;

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
       return OrthogonalMove.isValid(board, from, to);
    }
}
