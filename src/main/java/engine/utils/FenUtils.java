package engine.utils;

import chess.PlayerColor;
import engine.Board;
import engine.piece.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

// The FEN notation (as described in https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation)
public class FenUtils {
    private FenUtils() {}

    public static Board load(String notation) {
        Board board = new Board();

        Reader reader = new StringReader(notation);

        // The fen notation starts with the 8th rank
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

                    board.put(piece, currentFile, currentRank);

                    currentFile++;
                }
            }

            return board;

        } catch (IOException e) {
            throw new RuntimeException("The FEN notation is invalid", e);
        }
    }
}
