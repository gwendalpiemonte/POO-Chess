package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.move.check.DiagonalMove;
import engine.move.check.OrthogonalMove;
import engine.move.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Queen extends SlidingPiece {
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
    public List<CardinalDirection> allowedDirections() {
        List<CardinalDirection> directions = new ArrayList<>(CardinalDirection.DIAGONALS);
        directions.addAll(CardinalDirection.ORTHOGONALS);

        return directions;
    }
}
