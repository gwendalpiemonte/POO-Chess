package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.piece.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ChessGame implements ChessController {
    static final String START_BOARD_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private PlayerColor currentPlayer;

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
        return false; // TODO
    }

    public void loadFenNotation(String notation) {
        // The FEN notation (as described in https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation)
        Reader reader = new StringReader(notation);
        // The fen notation starts with 8.
        int currentRank = 7;
        int currentFile = 0;
        int currentChar;
        try {
            while ((currentChar = reader.read()) != ' ') {
                if (currentChar == '/') {
                    currentRank--;
                    currentFile = 0;
                } else if (Character.isDigit(currentChar)) {
                    int value = currentChar - '0';
                    if ((value + currentFile) > 8) {
                        throw new RuntimeException("The FEN notation is invalid.");
                    }

                    currentFile += value;
                } else {
                    PlayerColor color = Character.isUpperCase(currentChar) ? PlayerColor.WHITE : PlayerColor.BLACK;
                    Piece piece = switch (Character.toLowerCase(currentChar)) {
                        case 'p' -> new Pawn(color);
                        case 'n' -> new Knight(color);
                        case 'b' -> new Bishop(color);
                        case 'r' -> new Rook(color);
                        case 'q' -> new Queen(color);
                        case 'k' -> new King(color);
                        default -> throw new RuntimeException("The FEN notation is invalid");
                    };

                    view.putPiece(piece.getType(), piece.getColor(), currentFile, currentRank);

                    currentFile++;
                }
            }

            currentChar = reader.read();
            // We skipped the space
            currentPlayer = currentChar == 'w' ? PlayerColor.WHITE : PlayerColor.BLACK;

            // For now, we ignore the rest.
        } catch (IOException e) {
            throw new RuntimeException("The FEN notation is invalid", e);
        }
    }


    @Override
    public void newGame() {
        loadFenNotation(START_BOARD_FEN);
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }
}
