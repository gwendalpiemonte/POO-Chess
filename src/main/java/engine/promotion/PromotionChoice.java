package engine.promotion;

import chess.ChessView;
import chess.PieceType;
import engine.piece.*;

/**
 * Represents a pawn promotion to an other piece type the player makes
 */
public class PromotionChoice implements ChessView.UserChoice {

    private final String choiceText;

    private final PieceType promotionPieceType;

    public PromotionChoice(String choiceText, PieceType promotionPieceType) {
        this.choiceText = choiceText;
        this.promotionPieceType = promotionPieceType;
    }

    @Override
    public String textValue() {
        return choiceText;
    }

    /**
     * Promotes the pawn into the given `promotionPieceType`.
     *
     * @param pawn The pawn to promote
     * @return The new piece created from the pawn
     */
    public Piece promote(Pawn pawn) {
        return switch (promotionPieceType) {
            case ROOK -> new Rook(pawn);
            case KNIGHT -> new Knight(pawn);
            case BISHOP -> new Bishop(pawn);
            case QUEEN -> new Queen(pawn);
            default -> throw new RuntimeException("Illegal promotion");
        };
    }
}
