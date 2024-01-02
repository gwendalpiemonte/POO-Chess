package engine;

import chess.PlayerColor;
import engine.piece.Pawn;
import engine.piece.Piece;

/**
 * Represents a chess board with its pieces
 */
public class Board {
    private final Piece[][] board;

    private PlayerColor currentPlayerColor;

    private Pawn enPassantCandidate;

    public Board() {
        board = new Piece[8][8];
        currentPlayerColor = PlayerColor.WHITE;
    }

    public void setEnPassantCandidate(Pawn enPassantCandidate) {
        this.enPassantCandidate = enPassantCandidate;
    }

    public void resetEnPassant() {
        this.enPassantCandidate = null;
    }

    public boolean isEnPassantCandidate(Piece piece) {
        if (enPassantCandidate != null) {
            return enPassantCandidate.equals(piece);
        } else {
            return false;
        }
    }

    /**
     * Either returns a piece at a given file and rank if one exists, otherwise returns null.
     *
     * @param file The file at which to retrieve the piece
     * @param rank The rank at which to retrieve the piece
     * @return The piece at the given file and rank
     */
    public Piece at(int file, int rank) {
        return board[file][rank];
    }

    /**
     * Either returns a piece at a given position if one exists, otherwise returns null.
     *
     * @param position The position at which to retrieve the piece
     * @return The piece at the given position
     */
    public Piece at(Position position) {
        return at(position.file(), position.rank());
    }

    /**
     * Put the given piece at the given file and rank.
     *
     * TODO: Should we provide an atomic way of moving pieces on the board ?
     *
     * @param piece The piece to put
     * @param file The file at which to put the piece
     * @param rank The rank at which to put the piece
     */
    public void put(Piece piece, int file, int rank) {
        board[file][rank] = piece;
    }

    /**
     * Removes a piece if one exists on a given file and rank
     *
     * @param file The file at which to remove the piece
     * @param rank The rank at which to remove the piece
     */
    public void remove(int file, int rank) {
       put(null, file, rank);
    }

    public PlayerColor getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    /**
     * TODO: This might be removed, we can swap the player color once a move is performed
     *
     * @param currentPlayerColor
     */
    public void setCurrentPlayerColor(PlayerColor currentPlayerColor) {
        this.currentPlayerColor = currentPlayerColor;
    }
}
