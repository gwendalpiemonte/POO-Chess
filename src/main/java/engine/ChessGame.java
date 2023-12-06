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
    private PlayerColor currentPlayer;

    private ChessView view;

    private final Piece[][] board;

    public ChessGame() {
        board = new Piece[8][8];
    }

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

    public void loadFenNotation(String notation) throws IOException {
        // The FEN notation (as described in https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation)
        Reader reader = new StringReader(notation);
        // The fen notation starts with 8.
        int currentRank = 7;
        int currentColumn = 0;
        int currentChar;
        while ((currentChar = reader.read()) != ' ') {
            if (currentChar == '/') {
                currentRank--;
                currentColumn = 0;
            } else if (Character.isDigit(currentChar)) {
                int value = currentChar - '0';
                if ((value + currentColumn) > 8) {
                    throw new IllegalArgumentException("The FEN notation is invalid.");
                }

                currentColumn += value;
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

                view.putPiece(piece.getType(), piece.getColor(), currentRank, currentColumn);

                currentColumn++;
            }
        }

        currentChar = reader.read();
        // We skipped the space
        currentPlayer = currentChar == 'w' ? PlayerColor.WHITE : PlayerColor.BLACK;

        // For now, we ignore the rest.
    }


    @Override
    public void newGame() {
        board[0][4] = new King(PlayerColor.WHITE);

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Piece piece = board[y][x];

                if (piece != null) {
                    view.putPiece(piece.getType(), piece.getColor(), x, y);
                }
            }
        }

        // view.putPiece(PieceType.KING, PlayerColor.WHITE, 4, 0); // TODO exemple uniquement
        view.putPiece(PieceType.QUEEN, PlayerColor.WHITE, 3, 0); // TODO exemple uniquement
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 5, 0); // TODO exemple uniquement
        view.putPiece(PieceType.BISHOP, PlayerColor.WHITE, 2, 0); // TODO exemple uniquement
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 1, 0); // TODO exemple uniquement
        view.putPiece(PieceType.KNIGHT, PlayerColor.WHITE, 6, 0); // TODO exemple uniquement
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 7, 0); // TODO exemple uniquement
        view.putPiece(PieceType.ROOK, PlayerColor.WHITE, 0, 0); // TODO exemple uniquement

        view.putPiece(PieceType.KING, PlayerColor.BLACK, 4, 7); // TODO exemple uniquement
        view.putPiece(PieceType.QUEEN, PlayerColor.BLACK, 3, 7); // TODO exemple uniquement
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 5, 7); // TODO exemple uniquement
        view.putPiece(PieceType.BISHOP, PlayerColor.BLACK, 2, 7); // TODO exemple uniquement
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 1, 7); // TODO exemple uniquement
        view.putPiece(PieceType.KNIGHT, PlayerColor.BLACK, 6, 7); // TODO exemple uniquement
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 7, 7); // TODO exemple uniquement
        view.putPiece(PieceType.ROOK, PlayerColor.BLACK, 0, 7); // TODO exemple uniquement

        for(int i = 0; i<8; ++i){
            view.putPiece(PieceType.PAWN, PlayerColor.WHITE, i, 1); // TODO exemple uniquement
        }

        for(int i = 0; i<8; ++i){
            view.putPiece(PieceType.PAWN, PlayerColor.BLACK, i, 6); // TODO exemple uniquement
        }
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }
}
