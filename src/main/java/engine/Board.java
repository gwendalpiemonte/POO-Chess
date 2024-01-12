package engine;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.bitboard.Bitboard;
import engine.piece.King;
import engine.piece.Pawn;
import engine.piece.Piece;
import engine.piece.traits.MoveListener;
import engine.piece.traits.PromotablePiece;
import engine.promotion.PromotionChoice;
import engine.promotion.PromotionUtils;

import java.util.*;
import java.util.stream.Stream;

/**
 * Represents a chess board with its pieces
 */
public class Board implements Cloneable {
    private final Piece[][] board;

    private PlayerColor currentPlayerColor;

    private Move lastMove;

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
        lastMove = other.lastMove;
        // Do not transfer the views!
    }

    public void putView(ChessView view) {
        views.add(view);
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    public boolean isMoveValid(Position from, Position to) {
        // Check pseudo legal move
        Piece piece = at(from);

        if (piece == null) {
            return false;
        }

        if (piece.getColor() != getCurrentPlayerColor()) {
            return false;
        }

        Bitboard moves = piece.getMoves(this, from);

        if (!moves.get(to)) {
            // Illegal move
            return false;
        }

        // Simulate move for checks
        return !moveCausesCheck(from, to);
    }

    public Optional<PromotionChoice[]> getPromotion(Position from, Position to) {
        Piece piece = at(from);
        if (piece instanceof PromotablePiece promotablePiece
                && promotablePiece.shouldPromote(to)) {
            return Optional.of(PromotionUtils.getPromotionChoices(promotablePiece.getPromotionChoices()));
        } else {
            return Optional.empty();
        }
    }

    public void apply(Position from, Position to) {
        // You should not call without a choice if it is a promotion
        assert getPromotion(from, to).isEmpty();

        applyInternal(from, to);
    }

    public void apply(Position from, Position to, PromotionChoice promotionChoice) {
        // You should not call with a choice if it is  not a promotion
        assert getPromotion(from, to).isPresent();

        applyInternal(from, to);

        // Replace the target
        Piece promoted = at(to);
        put(promotionChoice.promote(promoted), to);
    }

    public boolean isInCheck(PlayerColor playerColor) {
        Position playerKingPosition = getPlayerKingPosition(getCurrentPlayerColor());
        // This conditions allows some tests to pass.

        if (playerKingPosition == null) {
            return false;
        }

        return stream()
                // For all opponent's pieces...
                .filter(piece -> piece.getValue().getColor() != playerColor)
                // ...get their possible moves...
                .map(piece -> piece.getValue().getMoves(this, piece.getKey()))
                // ...combine them...
                .collect(Bitboard.collect())
                // ...and see if the king is attacked.
                .get(playerKingPosition);
    }

    private boolean moveCausesCheck(Position from, Position to) {
        Board copy = new Board(this);

        copy.applyInternal(from, to);

        return copy.isInCheck(getCurrentPlayerColor());
    }

    // This function exists for the moveCausesCheck function. If not, the promotion assertion would get
    // caught up at times.
    private void applyInternal(Position from, Position to) {
        Piece piece = at(from);

        Piece target = at(to);
        if (piece.getCaptures(this, from).get(to)) {
            if (target == null && piece instanceof Pawn) {
                // En-passant, so delete the piece from the last move
                remove(lastMove.to());
            } else {
                // This is considered a capture, so we delete the piece at the destination (or throw an error if empty)
                assert target != null;
                remove(to);
            }
        } else {
            // This is not a capture, but still check if the piece is null, as a safety measure.
            assert target == null;
        }

        remove(from);
        put(piece, to);

        if (piece instanceof MoveListener listener) {
            listener.onMove();
        }

        lastMove = new Move(from, to);
    }

    /**
     * Get the position of the given player color king
     *
     * @param playerColor The color of the player of whom to get the king's position
     * @return The position of the king
     */
    private Position getPlayerKingPosition(PlayerColor playerColor) {
        return stream()
                .filter(e -> e.getValue() instanceof King)
                .filter(e -> e.getValue().getColor() == playerColor)
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
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


    public static PlayerColor getOppositeColor(PlayerColor color) {
        return switch (color) {
            case WHITE -> PlayerColor.BLACK;
            case BLACK -> PlayerColor.WHITE;
        };
    }

    public Bitboard getPlayerOccupation(PlayerColor color) {
        return stream()
                .filter(piece -> piece.getValue().getColor() == color)
                .map(Map.Entry::getKey)
                .collect(Bitboard.collectPositions());
    }


    public Bitboard getOccupationBoard() {
        return stream()
                .map(Map.Entry::getKey)
                .collect(Bitboard.collectPositions());
    }


    // We suppress this warning, as the clone method is re-implemented from within the constructor
    // (as to allow for replacement of the board)
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Board clone() {
        return new Board(this);
    }
}
