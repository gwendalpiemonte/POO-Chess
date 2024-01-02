package engine.utils;

import chess.PlayerColor;
import engine.Board;
import engine.ChessGame;
import engine.piece.Pawn;
import engine.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FenUtilsTest {

    @Test
    void testSimpleSetup() {
        Board board = FenUtils.load(ChessGame.START_BOARD_FEN);

        assertThat(board.getCurrentPlayerColor()).isEqualTo(PlayerColor.WHITE);
    }

    @Test
    void testBlackAsCurrentPlayer() {
        Board board = FenUtils.load(ChessGame.START_BOARD_FEN);

        assertThat(board.getCurrentPlayerColor()).isEqualTo(PlayerColor.WHITE);
    }

    @Test
    void testEnPassantWhite() {
        Board board = FenUtils.load("k7/8/8/4pP2/8/8/8/K7 w - e6 0 1");

        Piece piece = board.at(CoordinateUtils.fromString("e5"));

        assertThat(piece)
                .isNotNull()
                .isInstanceOf(Pawn.class);

        boolean isEnPassant = board.isEnPassantCandidate(piece);

        assertThat(isEnPassant).isTrue();
    }

    @Test
    void testEnPassantBlack() {
        Board board = FenUtils.load("k7/8/8/8/4pP2/8/8/K7 b - f3 0 1");

        Piece piece = board.at(CoordinateUtils.fromString("f4"));

        assertThat(piece)
                .isNotNull()
                .isInstanceOf(Pawn.class);

        boolean isEnPassant = board.isEnPassantCandidate(piece);

        assertThat(isEnPassant).isTrue();
    }
}