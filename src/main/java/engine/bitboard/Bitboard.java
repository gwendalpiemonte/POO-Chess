package engine.bitboard;

public class Bitboard {
    /**
     * Represents an 8x8 board where the LSB is in the top-left and the MSB is in the bottom-right
     */
    private final long value;

    public Bitboard() {
        this(0);
    }

    private Bitboard(long value) {
        this.value = value;
    }

    public Bitboard and(Bitboard board) {
        return new Bitboard(this.value & board.value);
    }

    public Bitboard or(Bitboard board) {
        return new Bitboard(this.value | board.value);
    }

    public Bitboard xor(Bitboard board) {
        return new Bitboard(this.value ^ board.value);
    }
}
