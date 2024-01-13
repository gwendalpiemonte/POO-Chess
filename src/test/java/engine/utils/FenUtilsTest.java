package engine.utils;

import chess.PlayerColor;
import engine.Board;
import engine.ChessGame;
import engine.Move;
import engine.Position;
import engine.piece.Pawn;
import engine.piece.Piece;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FenUtilsTest {

    @Test
    void testSimpleSetup() {
        Board board = FenParser.load(ChessGame.START_BOARD_FEN);

        assertThat(board.getCurrentPlayerColor()).isEqualTo(PlayerColor.WHITE);
    }

    @Test
    void testBlackAsCurrentPlayer() {
        Board board = FenParser.load(ChessGame.START_BOARD_FEN);

        assertThat(board.getCurrentPlayerColor()).isEqualTo(PlayerColor.WHITE);
    }

    @Test
    void testEnPassantWhite() {
        Board board = FenParser.load("k7/8/8/4pP2/8/8/8/K7 w - e6 0 1");

        Move lastMove = board.getLastMove();

        assertThat(lastMove).usingRecursiveComparison()
                .isEqualTo(new Move(
                        Position.fromString("e7"),
                        Position.fromString("e5")
                ));
    }

    @Test
    void testEnPassantBlack() {
        Board board = FenParser.load("k7/8/8/8/4pP2/8/8/K7 b - f3 0 1");

        Move lastMove = board.getLastMove();

        assertThat(lastMove).usingRecursiveComparison()
                .isEqualTo(new Move(
                        Position.fromString("f2"),
                        Position.fromString("f4")
                ));
    }
}