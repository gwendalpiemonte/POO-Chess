package engine;

import chess.ChessView;
import chess.PlayerColor;
import engine.utils.CoordinateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChessGameTest {
    @Mock
    ChessView chessView;

    @Test
    void testBlankBoard() {
        ChessGame chessGame = new ChessGame();
        chessGame.start(chessView);

        chessGame.newGame();


        // Checks that the board contains the 16 required pieces on each side
        verify(chessView, times(16)).putPiece(any(), eq(PlayerColor.WHITE), anyInt(), anyInt());

        verify(chessView, times(16)).putPiece(any(), eq(PlayerColor.BLACK), anyInt(), anyInt());

        assertThat(chessGame.getCurrentPlayer()).isEqualTo(PlayerColor.WHITE);
    }

    @Test
    void testMoveBlackPieceOnGameStart() {
        ChessGame chessGame = new ChessGame();
        chessGame.start(chessView);

        chessGame.newGame();

        Position from = CoordinateUtils.fromString("d7");
        Position to = CoordinateUtils.fromString("d5");

        assertThat(chessGame.move(from.rank(), from.file(), to.rank(), to.file())).isFalse();
    }
}