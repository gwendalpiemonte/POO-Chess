package engine.piece.traits;

import chess.PieceType;
import engine.Position;
import engine.promotion.PromotionChoice;

import java.util.List;

/**
 * This is a marker class for the {@link engine.Board} to ask the user the wanted promotion.
 * <br/>
 * This allows for extensibility.
 */
public interface PromotablePiece {
    List<PieceType> getPromotionChoices();

    boolean shouldPromote(Position position);
}
