package engine.utils;

import chess.PlayerColor;
import engine.Board;
import engine.Move;
import engine.Position;
import engine.piece.*;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Creates a new board with in a particular game position for the given FEN string
 * <p>
 * About the FEN notation: <a href="https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation">...</a>
 */
public class FenParser {
    private FenParser() {
    }

    public static Board load(String notation) {
        Board board = new Board();
        Reader reader = new StringReader(notation);

        try {
            parsePiecePlacement(reader, board);
            parseActiveColor(reader, board);
            parseCastlingAvailability(reader, board);
            parseEnPassant(reader, board);

            // We could also parse halfmove clock and fullmove number, but it is out-of-scope for now.

            return board;
        } catch (IOException e) {
            throw new RuntimeException("The FEN notation is invalid", e);
        }
    }

    private static void parsePiecePlacement(Reader reader, Board board) throws IOException {
        // The fen notation starts with the 8th rank
        int currentRank = 7;
        int currentFile = 0;
        int currentChar;

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
                    case 'r' -> {
                        Rook rook = new Rook(color);
                        // We set the flag to possibly reset it later.
                        // This avoids a lookahead in the FEN string.
                        rook.setHasMoved(true);
                        yield rook;
                    }
                    case 'q' -> new Queen(color);
                    case 'k' -> {
                        King king = new King(color);
                        king.setHasMoved(true);
                        yield king;
                    }
                    default -> throw new RuntimeException("The FEN notation is invalid");
                };

                board.put(piece, currentFile, currentRank);

                currentFile++;
            }
        }
    }

    private static void parseActiveColor(Reader reader, Board board) throws IOException {
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
    }

    private static void parseCastlingAvailability(Reader reader, Board board) throws IOException {
        int currentChar = reader.read();

        if (currentChar != '-') {
            do {
                PlayerColor player = Character.isUpperCase(currentChar) ? PlayerColor.WHITE : PlayerColor.BLACK;
                // You can always set the king as not moved if a given player can castle
                int homeRank = player == PlayerColor.WHITE ? 0 : 7;
                ((King) board.at(4, homeRank)).setHasMoved(false);

                switch (Character.toLowerCase(currentChar)) {
                    case 'k' -> ((Rook) board.at(7, homeRank)).setHasMoved(false);
                    case 'q' -> ((Rook) board.at(0, homeRank)).setHasMoved(false);
                }

            } while ((currentChar = reader.read()) != ' ');
        } else {
            reader.skip(1);
        }
    }

    private static void parseEnPassant(Reader reader, Board board) throws IOException {
        int currentChar = reader.read();

        if (currentChar != '-') {
            String value = String.valueOf(new char[]{(char) currentChar, (char) reader.read()});

            Position enPassantPos = Position.fromString(value);
            // in the FEN notation, this position is the case where the pawn can go to do an en-passant.
            // To get the actual piece, we need to get the new position.
            // (As it is the piece of the non-playing player, we invert the direction from the expected one)
            int rankDiff = board.getCurrentPlayerColor() == PlayerColor.WHITE ? +1 : -1;

            board.setLastMove(new Move(
                    new Position(enPassantPos.file(), enPassantPos.rank() + rankDiff),
                    new Position(enPassantPos.file(), enPassantPos.rank() - rankDiff)
            ));
        }
    }
}
