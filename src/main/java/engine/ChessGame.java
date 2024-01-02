package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;
import engine.promotion.*;
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
        System.out.println(String.format("TO REMOVE : from (%d, %d) to (%d, %d)", fromX, fromY, toX, toY)); // TODO remove

        Position from = new Position(fromX, fromY);
        Position to = new Position(toX, toY);
        Piece piece = board.at(from);

        // No piece on the given square
        if (piece == null) {
            return false;
        }

        if (piece.getColor() != board.getCurrentPlayerColor()) {
            return false;
        }

        if (!piece.isMoveValid(board, from, to)) {
            return false;
        }

        board.resetEnPassant();

        board.put(piece, to.file(), to.rank());
        board.remove(from.file(), from.rank());

        if (piece instanceof Pawn pawn) {
            if (shouldPromote(pawn, to)) {
                PromotionChoice choice = view.askUser(
                        "Promotion",
                        "En quelle pièce souhaitez-vous promouvoir votre pion ?",
                        new PromotionChoice("Tour", PieceType.ROOK),
                        new PromotionChoice("Cavalier", PieceType.KNIGHT),
                        new PromotionChoice("Fou", PieceType.BISHOP),
                        new PromotionChoice("Dame", PieceType.QUEEN)
                );

                piece = choice.promote(pawn);
                board.put(piece, to.file(), to.rank());
            } else if (Pawn.isDoubleAdvance(from, to)) {
                // Mark the piece as the candidate for an en-passant
                board.setEnPassantCandidate(pawn);
            }

            // TODO: Check for checks (hehe)
        }
        board.setCurrentPlayerColor(board.getCurrentPlayerColor() == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE);

        view.removePiece(from.file(), from.rank());
        view.putPiece(piece.getType(), piece.getColor(), to.file(), to.rank());

        return true; // TODO
    }

    boolean shouldPromote(Piece piece, Position position) {
        return switch (piece.getColor()) {
            case WHITE -> position.rank() == 7;
            case BLACK -> position.rank() == 0;
        };
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
    }

    @Override
    public void newGame() {
        init(FenUtils.load(START_BOARD_FEN));
    }

    public PlayerColor getCurrentPlayer() {
        return board.getCurrentPlayerColor();
    }
}
