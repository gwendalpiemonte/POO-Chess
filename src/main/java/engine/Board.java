package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.piece.Pawn;
import engine.piece.Piece;
import engine.promotion.PromotionChoice;
import engine.move.Move;
import engine.move.PromotionMove;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chess board with its pieces
 */
public class Board {
    private final Piece[][] board;

    private PlayerColor currentPlayerColor;

    private Pawn enPassantCandidate;

    private List<ChessView> views = new ArrayList<>();

    public Board() {
        board = new Piece[8][8];
        currentPlayerColor = PlayerColor.WHITE;
    }

    public void putView(ChessView view) {
        views.add(view);
    }

    public void setEnPassantCandidate(Pawn enPassantCandidate) {
        this.enPassantCandidate = enPassantCandidate;
    }

    public void resetEnPassant() {
        this.enPassantCandidate = null;
    }

    public boolean isEnPassantCandidate(Piece piece) {
        if (enPassantCandidate == null) {
            return false;
        }

        return enPassantCandidate.equals(piece);
    }

    public Move getMoveFor(Position from, Position to) {
        Piece piece = at(from);

        // No piece on the given square
        if (piece == null) {
            System.out.println("No piece");
            return Move.illegal();
        }

        if (piece.getColor() != getCurrentPlayerColor()) {
            System.out.println("Not your turn");
            return Move.illegal();
        }

        return piece.getMoveFor(this, from, to);
    }

    public void apply(Move move) {
        // Reset the en-passant before moving (as the move sets it)
        resetEnPassant();
        move.move(this);
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
        if (piece != null) {
            views.forEach(v -> v.putPiece(piece.getType(), piece.getColor(), file, rank));
        } else {
            views.forEach(v -> v.removePiece(file, rank));
        }

        board[file][rank] = piece;
    }

    public void put(Piece piece, Position position) {
        put(piece, position.file(), position.rank());
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

    /**
     * Removes a piece if one exists at a given position
     *
     * @param position The position at which to remove the piece
     */
    public void remove(Position position) {
        remove(position.file(), position.rank());
    }

    /**
     * Get the color of the player which should play the next move
     *
     * @return The current player color
     */
    public PlayerColor getCurrentPlayerColor() {
        return currentPlayerColor;
    }

    /**
     * TODO: This might be removed, we can swap the player color once a move is performed. This is used to set the player color when creating a board from a FEN string
     *
     * @param currentPlayerColor
     */
    public void setCurrentPlayerColor(PlayerColor currentPlayerColor) {
        this.currentPlayerColor = currentPlayerColor;
    }
}
