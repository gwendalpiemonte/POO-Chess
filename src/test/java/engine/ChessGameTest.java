package engine;

import chess.ChessView;
import chess.PlayerColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChessGameTest {
    @Mock
    ChessView chessView;

    @Test
    void testNotation() throws IOException {
        ChessGame chessGame = new ChessGame();
        chessGame.start(chessView);
        chessGame.loadFenNotation("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");

        verify(chessView, times(16))
                .putPiece(any(), eq(PlayerColor.WHITE), anyInt(), anyInt());

        verify(chessView, times(16))
                .putPiece(any(), eq(PlayerColor.BLACK), anyInt(), anyInt());

        assertThat(chessGame.getCurrentPlayer())
                .isEqualTo(PlayerColor.WHITE);
    }
}