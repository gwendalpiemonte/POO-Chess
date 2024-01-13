package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.bitboard.Bitboard;
import engine.piece.King;
import engine.piece.Pawn;
import engine.piece.Piece;
import engine.piece.traits.HasSpecialMove;
import engine.piece.traits.MoveListener;
import engine.piece.traits.PromotablePiece;
import engine.promotion.PromotionChoice;
import engine.promotion.PromotionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Represents a chess board with its pieces.
 * Solves checked positions by copying the board internally.
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

    public void registerView(ChessView view) {
        views.add(view);
    }

    public Move getLastMove() {
        return lastMove;
    }

    public void setLastMove(Move lastMove) {
        this.lastMove = lastMove;
    }

    /**
     * Checks if the move is valid using the current board state
     *
     * @param move The move to check
     * @return true if the move is valid, false otherwise
     */
    public boolean isMoveValid(Move move) {
        // Check pseudo legal move
        Piece piece = at(move.from());

        if (piece == null) {
            return false;
        }

        if (piece.getColor() != getCurrentPlayerColor()) {
            return false;
        }

        Bitboard moves = piece.getMoves(this, move.from());

        // As we are in the validate function, we can or the special moves
        if (piece instanceof HasSpecialMove specialMovePiece) {
            moves = moves
                    .or(specialMovePiece.getSpecialMoves(this, move.from()));
        }

        if (!moves.get(move.to())) {
            // Illegal move
            return false;
        }

        // Simulate move for checks
        return !moveCausesCheck(move, getCurrentPlayerColor());
    }

    /**
     * Returns a {@link PromotionChoice} given the move of a piece on the board.
     *
     * @param move The move to check
     * @return An optional {@link PromotionChoice} that represents the promotion of the piece
     */
    public Optional<PromotionChoice[]> getPromotion(Move move) {
        Piece piece = at(move.from());
        if (piece instanceof PromotablePiece promotablePiece
                && promotablePiece.shouldPromote(move.to())) {
            return Optional.of(PromotionUtils.getPromotionChoices(promotablePiece.getPromotionChoices()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Applies the move to the current board state
     *
     * @param move The move to apply
     */
    public void apply(Move move) {
        // You should not call without a choice if it is a promotion
        assert getPromotion(move).isEmpty();

        applyInternal(move);

        changeTurn();
    }

    /**
     * Applies the move and promotionChoice to the current board state
     *
     * @param move The move to apply
     * @param promotionChoice The promotionChoice to apply
     */
    public void apply(Move move, PromotionChoice promotionChoice) {
        // You should not call with a choice if it is  not a promotion
        assert getPromotion(move).isPresent();

        applyInternal(move);

        // Replace the target
        Piece promoted = at(move.to());
        put(promotionChoice.promote(promoted), move.to());

        changeTurn();
    }

    /**
     * Checks whether the player with the given `playerColor` is in check.
     *
     * @param playerColor The color of the player for which to check
     * @return true if the player is in check, false otherwise
     */
    public boolean isInCheck(PlayerColor playerColor) {
        Position playerKingPosition = getPlayerKingPosition(getCurrentPlayerColor());
        // This conditions allows some tests to pass.

        if (playerKingPosition == null) {
            return false;
        }

        return getAttackedSquares(playerColor)
                // ...and see if the king is attacked.
                .get(playerKingPosition);
    }

    public boolean hasLegalMove(PlayerColor color) {
        return stream()
                .filter(e -> e.getValue().getColor() == color)
                .anyMatch(entry -> {
                    Bitboard legalMoves = getLegalMoves(entry.getKey(), color);

                    return !legalMoves.isEmpty();
                });
    }

    private Bitboard getLegalMoves(Position piecePosition, PlayerColor color) {
        return at(piecePosition).getMoves(this, piecePosition).stream()
                .filter(move -> !moveCausesCheck(new Move(piecePosition, move), color))
                .collect(Bitboard.collectPositions());
    }

    /**
     * Get all the attacked squares for a given `playerColor`.
     *
     * @param playerColor The player color for which to get the attacked squares
     * @return A bitboard representing the attacked squares
     */
    public Bitboard getAttackedSquares(PlayerColor playerColor) {
        return stream()
                // For all opponent's pieces...
                .filter(piece -> piece.getValue().getColor() != playerColor)
                // ...get their possible moves...
                .map(piece -> piece.getValue().getMoves(this, piece.getKey()))
                // ...combine them.
                .collect(Bitboard.collect());
    }

    /**
     * Checks whether the move causes a check. This method copies the board and applies the move on the
     * copy.
     *
     * @param move The move to check
     * @return true if the move causes a check, false otherwise
     */
    private boolean moveCausesCheck(Move move, PlayerColor color) {
        Board copy = new Board(this);

        copy.applyInternal(move);

        return copy.isInCheck(color);
    }

    // This function exists for the moveCausesCheck function. If not, the promotion assertion would get
    // caught up at times.
    private void applyInternal(Move move) {
        Piece piece = at(move.from());

        if (piece instanceof HasSpecialMove specialMovePiece
                && specialMovePiece.getSpecialMoves(this, move.from()).get(move.to())) {

            specialMovePiece.applySpecialMove(this, move);

            return;
        }

        Piece target = at(move.to());
        if (piece.getCaptures(this, move.from()).get(move.to())) {
            if (target == null && piece instanceof Pawn) {
                // En-passant, so delete the piece from the last move
                remove(lastMove.to());
            } else {
                // This is considered a capture, so we delete the piece at the destination (or throw an error if empty)
                assert target != null;
                remove(move.to());
            }
        } else {
            // This is not a capture, but still check if the piece is null, as a safety measure.
            assert target == null;
        }

        remove(move.from());
        put(piece, move.to());

        if (piece instanceof MoveListener listener) {
            listener.onMove();
        }

        lastMove = move;
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
     * Put the given piece at the given file and rank.
     *
     * @param piece The piece to put
     * @param file  The file at which to put the piece
     * @param rank  The rank at which to put the piece
     */
    public void put(Piece piece, int file, int rank) {
        notifyViews(piece, file, rank);

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

    /**
     * Change the current player to the opposite colored one
     */
    private void changeTurn() {
        currentPlayerColor = getOppositeColor(currentPlayerColor);
    }

    private void notifyViews(Piece piece, int file, int rank) {
        views.forEach(view -> notifyView(view, piece, file, rank));
    }

    private void notifyView(ChessView view, Piece piece, int file, int rank) {
        if (piece != null) {
            view.putPiece(piece.getType(), piece.getColor(), file, rank);
        } else {
            view.removePiece(file, rank);
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
