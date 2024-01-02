package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;
import engine.promotion.*;
import engine.move.Move;
import engine.utils.FenUtils;

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
        Position from = new Position(fromX, fromY);
        Position to = new Position(toX, toY);

        Move move = board.getMoveFor(from, to);

        if (!move.isValid()) {
            System.out.println("Invalid move.");
            return false;
        }

        move.addPrompt(() -> view.askUser(
                "Promotion",
                "En quelle pièce souhaitez-vous promouvoir votre pion ?",
                new PromotionChoice("Tour", PieceType.ROOK),
                new PromotionChoice("Cavalier", PieceType.KNIGHT),
                new PromotionChoice("Fou", PieceType.BISHOP),
                new PromotionChoice("Dame", PieceType.QUEEN)
        ));

        board.apply(move);
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
        init(FenUtils.load(START_BOARD_FEN));
    }

    public PlayerColor getCurrentPlayer() {
        return board.getCurrentPlayerColor();
    }
}
