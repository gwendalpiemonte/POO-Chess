package engine.bitboard;

import engine.Position;

public class Bitboard {
    /**
     * Represents an 8x8 board where the LSB is in the bottom-left and the MSB is in the top-right.
     *
     * This attribute is package-private in order to be used in tests.
     */
    long value;

    public Bitboard() {
        this(0);
    }

    private Bitboard(long value) {
        this.value = value;
    }

    /**
     * Creates a new bitboard with the square at `position` set to one.
     *
     * @param position The position of the square to set at one
     * @return
     */
    public static Bitboard single(Position position) {
        return new Bitboard(1L << index(position));
    }

    /**
     * Returns the index of a given position
     *
     * @param position The position at which to get the index
     * @return The index of the position
     */
    private static long index(Position position) {
        return position.file() + position.rank() * 8L;
    }

    /**
     * Set the bit at position `position` to the value `value`
     *
     * @param position The position to set
     * @param value The value to set at the position
     */
    public void set(Position position, boolean value) {
        long index = index(position);
        this.value = (this.value & ~(1L << index)) | ((value ? 1L : 0L) << index);
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

    /**
     * Returns a new instance of a bitboard with squares inverted.
     *
     * @return The inverted bitboard
     */
    public Bitboard not() {
        return new Bitboard(~value);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
