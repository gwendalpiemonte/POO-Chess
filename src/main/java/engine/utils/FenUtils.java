package engine.utils;

import chess.PlayerColor;
import engine.Board;
import engine.Position;
import engine.piece.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

// The FEN notation (as described in https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation)
public class FenUtils {
    private FenUtils() {
    }

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

            char activeColor = (char) reader.read();
            switch (activeColor) {
                case 'w':
                    board.setCurrentPlayerColor(PlayerColor.WHITE);
                    break;
                case 'b':
                    board.setCurrentPlayerColor(PlayerColor.BLACK);
                    break;
                default:
                    throw new RuntimeException("Invalid active color");
            }
            // Skip a character (the space)
            reader.skip(1);
            // parse castling status
            currentChar = reader.read();

            if (currentChar != '-') {
                // TODO.
                do {

                } while ((currentChar = reader.read()) != ' ');
            } else {
                reader.skip(1);
            }

            // parse the en-passant case
            currentChar = reader.read();

            if (currentChar != '-') {
                String value = String.valueOf(new char[]{(char) currentChar, (char) reader.read()});

                Position enPassantPos = Position.fromString(value);
                // in the FEN notation, this position is the case where the pawn can go to do an en-passant.
                // To get the actual piece, we need to get the new position.
                // (As it is the piece of the non-playing player, we invert the direction from the expected one)
                int rankDiff = board.getCurrentPlayerColor() == PlayerColor.WHITE ? -1 : +1;
                Position piecePos = new Position(enPassantPos.file(), enPassantPos.rank() + rankDiff);

                board.setEnPassantCandidate((Pawn) board.at(piecePos));
            }

            return board;

        } catch (IOException e) {
            throw new RuntimeException("The FEN notation is invalid", e);
        }
    }
}
