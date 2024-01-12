package engine.promotion;

import chess.PieceType;

import java.util.List;
import java.util.Map;

public class PromotionUtils {
    private static final Map<PieceType, String> DISPLAY_NAMES = Map.of(
            // Even if it is not used, it is still defined here to ensure that in the case of a modified version,
            // the developer doesn't have to wonder why the pawn is not defined.
            PieceType.PAWN, "Pion",
            PieceType.ROOK, "Tour",
            PieceType.KNIGHT, "Cavalier",
            PieceType.BISHOP,"Fou",
            PieceType.QUEEN, "Dame"
    );

    public static PromotionChoice[] getPromotionChoices(List<PieceType> types) {
        return types.stream()
                .map(pieceType -> new PromotionChoice(DISPLAY_NAMES.get(pieceType), pieceType))
                .toArray(PromotionChoice[]::new);
    }
}
