package engine.bitboard;

import engine.Position;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

/**
 * A site that can help you create tests : https://gekomad.github.io/Cinnamon/BitboardCalculator/
 * The Bitboard class implements the `Layout 2` layout.
 */
public class BitboardTest {
    @Test
    void testSingleA1() {
        Bitboard bitboard = Bitboard.single(Position.fromString("a1"));

        assertThat(bitboard.value).isEqualTo(1);
    }

    @Test
    void testSingleH8() {
        Bitboard bitboard = Bitboard.single(Position.fromString("h8"));

        assertThat(bitboard.value).isEqualTo(1L << 63);
    }

    @Test
    void testSingleA8() {
        Bitboard bitboard = Bitboard.single(Position.fromString("a8"));

        assertThat(bitboard.value).isEqualTo(1L << 56);
    }

    @Test
    void testSingleH1() {
        Bitboard bitboard = Bitboard.single(Position.fromString("h1"));

        assertThat(bitboard.value).isEqualTo(1L << 7);
    }

    @Test
    void testSetPosition() {
        Bitboard bitboard = new Bitboard();
        bitboard.set(Position.fromString("c3"), true);

        assertThat(bitboard.value).isEqualTo(262144);
    }

    @Test
    void testOrthogonalBitboard() {
        Bitboard bitboard = Bitboard.orthogonal(Position.fromString("f3"));

        assertThat(bitboard.value).isEqualTo(2314885530833068064L);
    }

    @Test
    void testDiagonalBitboard() {
        Bitboard bitboard = Bitboard.diagonal(Position.fromString("b4"));

        assertThat(bitboard.value).isEqualTo(2310355426442807312L);
    }

    @Test
    void testFileBitboard() {
        Bitboard bitboard = Bitboard.file(3);

        assertThat(bitboard.value).isEqualTo(4278190080L);
    }

    @Test
    void testRankBitboard() {
        Bitboard bitboard = Bitboard.rank(5);

        assertThat(bitboard.value).isEqualTo(2314885530818453536L);
    }

    @Test
    void testBitboardSet() {
        Bitboard bitboard = new Bitboard();

        bitboard.set(Position.fromString("c4"), true);
        bitboard.set(Position.fromString("e6"), true);

        assertThat(bitboard.value).isEqualTo(17592253153280L);
    }

    @Test
    void testBitboardNot() {
        Bitboard bitboard = Bitboard.single(Position.fromString("g4"));

        assertThat(bitboard.not().value).isEqualTo(-1073741825L);
    }

    @Test
    void testBitboardAnd() {
        Bitboard b1 = Bitboard.diagonal(Position.fromString("d6"));
        Bitboard b2 = new Bitboard();

        b2.set(Position.fromString("c5"), true);
        b2.set(Position.fromString("b4"), true);

        assertThat(b1.and(b2).value).isEqualTo(17213423616L);
    }

    @Test
    void testBitboardOr() {
        Bitboard b1 = Bitboard.orthogonal(Position.fromString("f5"));
        Bitboard b2 = new Bitboard();

        b2.set(Position.fromString("e2"), true);
        b2.set(Position.fromString("d7"), true);

        assertThat(b1.or(b2).value).isEqualTo(2317138288409849888L);
    }

    @Test
    void testBitboardXor() {
        Bitboard b1 = new Bitboard();
        Bitboard b2 = new Bitboard();

        b1.set(Position.fromString("d5"), true);
        b1.set(Position.fromString("f2"), true);

        b2.set(Position.fromString("c5"), true);
        b2.set(Position.fromString("f2"), true);

        assertThat(b1.xor(b2).value).isEqualTo(51539607552L);
    }

    @Test
    void testBitboardEmpty() {
        Bitboard bitboard = new Bitboard();

        assertThat(bitboard.isEmpty()).isTrue();
    }

    @Test
    void testBitboardNotEmpty() {
        Bitboard bitboard = Bitboard.single(Position.fromString("a4"));

        assertThat(bitboard.isEmpty()).isFalse();
    }

    @Test
    void testStreamCollector() {
        Bitboard collected = Stream.of(
                Bitboard.single(Position.fromString("a1")),
                Bitboard.single(Position.fromString("b1"))
        ).collect(Bitboard.collect());

        assertThat(collected.value)
                .isEqualTo(3);
    }
}
