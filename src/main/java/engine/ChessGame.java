package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.piece.*;
import engine.promotion.*;
import engine.utils.FenParser;

import java.util.Optional;

public class ChessGame implements ChessController {
    /**
     * The starting board position in FEN notation
     */
    public static final String START_BOARD_FEN = "rn2k1nr/pp4pp/3p4/q1pP1p2/P1P4b/1b2pPRP/1P1NP1PQ/2B1KBNR b Kkq - 0 1";

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

        Move move = new Move(new Position(fromX, fromY), new Position(toX, toY));

        if (!board.isMoveValid(move)) {
            return false;
        }

        Optional<PromotionChoice[]> promotion = board.getPromotion(move);
        if (promotion.isPresent()) {
            PromotionChoice choice = view.askUser(
                    "Promotion",
                    "En quelle pièce souhaitez-vous promouvoir votre pion ?",
                    promotion.get()
            );

            board.apply(move, choice);
        } else {
            board.apply(move);
        }

        if (!board.hasLegalMove(board.getCurrentPlayerColor())) {
            // We are either in a stalemate or a checkmate!
            if (board.isInCheck(board.getCurrentPlayerColor())) {
                view.displayMessage("Checkmate !");
            } else {
                view.displayMessage("Egalité par PAT !");
            }

            return true;
        }


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

        board.registerView(view);
    }


    @Override
    public void newGame() {
        init(FenParser.load(START_BOARD_FEN));
    }

    public PlayerColor getCurrentPlayer() {
        return board.getCurrentPlayerColor();
    }
}
