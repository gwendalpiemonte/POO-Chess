package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.bitboard.Bitboard;
import engine.move.Move;
import engine.piece.King;
import engine.piece.Pawn;
import engine.piece.Piece;

import java.util.*;
import java.util.stream.Stream;

/**
 * Represents a chess board with its pieces
 */
public class Board implements Cloneable {
    private final Piece[][] board;

    private PlayerColor currentPlayerColor;

    private Pawn enPassantCandidate;

    private final List<ChessView> views = new ArrayList<>();

    public Board() {
        board = new Piece[8][8];
        currentPlayerColor = PlayerColor.WHITE;
    }

    private Board(Board other) {
        board = new Piece[8][8];
        // Copy over all elements
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (other.board[i][j] != null) {
                    board[i][j] = other.board[i][j].clone();
                }
            }
        }

        currentPlayerColor = other.currentPlayerColor;
        // Do not transfer the views!
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

        Move moveFor = piece.getMoveFor(this, from, to);

        // Validate that no check is created by this move:
        Board cloned = clone();
        cloned.apply(moveFor);

        if (!cloned.getCheckAttackers().isEmpty()) {
            System.out.println("Check is not resolved, or move would create one.");
            return Move.illegal();
        }

        return moveFor;
    }

    /**
     * Get a list of positions that attacks the current player's king
     *
     * @return The list of positions
     */
    public List<Position> getCheckAttackers() {
        Position kingPosition = getPlayerKing(getCurrentPlayerColor());
        if (kingPosition == null) {
            // This would never happen in a real game, but we still want tests to pass.
            return List.of();
        }

        return getAttackersForPosition(getCurrentPlayerColor(), kingPosition);
    }

    /**
     * Get the position of the given player color king
     *
     * @param playerColor The color of the player of whom to get the king's position
     * @return The position of the king
     */
    private Position getPlayerKing(PlayerColor playerColor) {
        return stream()
                .filter(e -> e.getValue() instanceof King)
                .filter(e -> e.getValue().getColor() == playerColor)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Applies a given move to the board.
     *
     * @param move The move to apply.
     * @implNote All state specific to a turn is changed in this method
     * (e.g. the current player, or the en-passant state)
     */
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
     * Returns true if the square at `position` is occupied by a piece, false otherwise
     *
     * @param position The position at which to check
     * @return true if square is occupied, false otherwise
     */
    public boolean isOccupied(Position position) {
        return at(position) != null;
    }

    /**
     * Returns true if the square at `file` and `rank` is occupied by a piece, false otherwise
     *
     * @param file The file at which to check
     * @param rank The rank at which to rank
     * @return true if square is occupied, false otherwise
     */
    public boolean isOccupied(int file, int rank) {
        return at(file, rank) != null;
    }

    /**
     * Put the given piece at the given file and rank.
     *
     * @param piece The piece to put
     * @param file  The file at which to put the piece
     * @param rank  The rank at which to put the piece
     */
    public void put(Piece piece, int file, int rank) {
        notifyView(piece, file, rank);

        board[file][rank] = piece;
    }

    /**
     * Put the given piece at the given position.
     *
     * @param piece    The piece to put
     * @param position The position at which to put the piece
     */
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
     * @param currentPlayerColor The new current player
     */
    public void setCurrentPlayerColor(PlayerColor currentPlayerColor) {
        this.currentPlayerColor = currentPlayerColor;
    }

    public void changeTurn() {
        currentPlayerColor = getOppositeColor(currentPlayerColor);
    }

    private void notifyView(Piece piece, int file, int rank) {
        if (piece != null) {
            views.forEach(v -> v.putPiece(piece.getType(), piece.getColor(), file, rank));
        } else {
            views.forEach(v -> v.removePiece(file, rank));
        }
    }

    public List<Position> getAttackersForPosition(PlayerColor color, Position position) {
        PlayerColor opponentColor = getOppositeColor(color);

        // Use some stream magic to get what we want:
        return stream()
                .filter(piece -> piece.getValue().getColor() == opponentColor)
                .filter(piece -> piece.getValue().getPseudoLegalMove(this, piece.getKey(), position).isValid())
                .map(Map.Entry::getKey)
                .toList();
    }

    public Stream<Map.Entry<Position, Piece>> stream() {
        Stream.Builder<Map.Entry<Position, Piece>> builder = Stream.builder();
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                if (board[file][rank] != null) {
                    builder.accept(Map.entry(
                            new Position(file, rank),
                            board[file][rank]
                    ));
                }
            }
        }

        return builder.build();
    }


    private PlayerColor getOppositeColor(PlayerColor color) {
        return switch (color) {
            case WHITE -> PlayerColor.BLACK;
            case BLACK -> PlayerColor.WHITE;
        };
    }

    // We suppress this warning, as the clone method is re-implemented from within the constructor
    // (as to allow for replacement of the board)
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Board clone() {
        return new Board(this);
    }
}
