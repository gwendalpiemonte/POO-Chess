package engine.bitboard;

import engine.utils.CoordinateUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * A site that can help you create tests : https://gekomad.github.io/Cinnamon/BitboardCalculator/
 * The Bitboard class implements the `Layout 2` layout.
 */
public class BitboardTest {
    @Test
    void testSingleA1() {
        Bitboard bitboard = Bitboard.single(CoordinateUtils.fromString("a1"));

        assertThat(bitboard.value).isEqualTo(1);
    }

    @Test
    void testSingleH8() {
        Bitboard bitboard = Bitboard.single(CoordinateUtils.fromString("h8"));

        assertThat(bitboard.value).isEqualTo(1L << 63);
    }

    @Test
    void testSingleA8() {
        Bitboard bitboard = Bitboard.single(CoordinateUtils.fromString("a8"));

        assertThat(bitboard.value).isEqualTo(1L << 56);
    }

    @Test
    void testSingleH1() {
        Bitboard bitboard = Bitboard.single(CoordinateUtils.fromString("h1"));

        assertThat(bitboard.value).isEqualTo(1L << 7);
    }

    @Test
    void testSetPosition() {
        Bitboard bitboard = new Bitboard();
        bitboard.set(CoordinateUtils.fromString("c3"), true);

        assertThat(bitboard.value).isEqualTo(262144);
    }
}
