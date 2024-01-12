package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;
import engine.promotion.*;
import engine.utils.FenParser;

import java.util.Optional;

public class ChessGame implements ChessController {
    public static final String START_BOARD_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    private ChessView view;

    private Board board;

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        // We cannot move if no game was started, as the board was not initialized
        if (board == null) {
            return false;
        }

        Position from = new Position(fromX, fromY);
        Position to = new Position(toX, toY);

        if (!board.isMoveValid(from, to)) {
            return false;
        }

        Optional<PromotionChoice[]> promotion = board.getPromotion(from, to);
        if (promotion.isPresent()) {
            PromotionChoice choice = view.askUser(
                    "Promotion",
                    "En quelle pièce souhaitez-vous promouvoir votre pion ?",
                    promotion.get()
            );

            board.apply(from, to, choice);
        } else {
            board.apply(from, to);
        }

        board.changeTurn();

        if (board.isInCheck(board.getCurrentPlayerColor())) {
            view.displayMessage("Check !");
        }

        return true;
    }

    // Package-private in order to be usable in tests.
    void init(Board board) {
        this.board = board;
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Piece piece = board.at(file, rank);
                if (piece != null) {
                    view.putPiece(piece.getType(), piece.getColor(), file, rank);
                }
            }
        }

        board.putView(view);
    }


    @Override
    public void newGame() {
        init(FenParser.load(START_BOARD_FEN));
    }

    public PlayerColor getCurrentPlayer() {
        return board.getCurrentPlayerColor();
    }
}
