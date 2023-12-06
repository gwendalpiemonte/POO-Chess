package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

public class TestView implements ChessView {

    @Override
    public void startView() {
        // NO-OP
    }

    @Override
    public void removePiece(int x, int y) {
        // NO-OP
    }

    @Override
    public void putPiece(PieceType type, PlayerColor color, int x, int y) {
        // NO-OP
    }

    @Override
    public void displayMessage(String msg) {
        // NO-OP
    }

    @Override
    public <T extends UserChoice> T askUser(String title, String question, T... possibilities) {
        return null;
    }
}
