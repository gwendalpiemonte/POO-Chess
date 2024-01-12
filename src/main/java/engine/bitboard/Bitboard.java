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
        return new Bitboard(1L << position.index());
    }

    /**
     * Creates a bitboard with the file and rank set to 1 for the given position.
     *
     * @param position The position from which to fill the file and rank
     * @return The bitboard representing the orthogonal moves from the given position
     */
    public static Bitboard orthogonal(Position position) {
        long value = 0;

        long file = position.file();
        long rank = position.rank();

        for (int i = 0; i < 8; i++) {
            value |= 1L << (file + i * 8);
            value |= 1L << (rank * 8 + i);
        }

        return new Bitboard(value);
    }

    /**
     * Creates a bitboard with the diagonals set to 1.
     *
     * @param position The position from which to fill the diagonals
     * @return The bitboard representing the diagonal moves for the given position
     */
    public static Bitboard diagonal(Position position) {
        long index = position.index();
        long value = 1L << index;


        long file = position.file();
        long rank = position.rank();

        for (int i = 1; i < 8; i++) {
            if (rank + i < 8 && file + i < 8) {
                value |= 1L << ((rank + i) * 8 + file + i);
            }

            if (rank - i >= 0 && file - i >= 0) {
                value |= 1L << ((rank - i) * 8 + file - i);
            }

            if (rank + i < 8 && file - i >= 0) {
                value |= 1L << ((rank + i) * 8 + file - i);
            }

            if (rank - i >= 0 && file + i < 8) {
                value |= 1L << ((rank - i) * 8 + file + i);
            }
        }

        return new Bitboard(value);
    }

    /**
     * Creates a bitboard with all squares of the file set to 1
     *
     * @param file The file on which the squares should be set to one
     * @return The bitboard
     */
    public static Bitboard file(int file) {
        long value = 0;

        for (int i = 0; i < 8; i++) {
            value |= 1L << (file * 8 + i);
        }

        return new Bitboard(value);
    }

    /**
     * Creates a bitboard with all squares of the rank set to 1
     *
     * @param rank The rank on which the squares should be set to one
     * @return The bitboard
     */
    public static Bitboard rank(int rank) {
        long value = 0;

        for (int i = 0; i < 8; i++) {
            value |= 1L << (rank + i * 8);
        }

        return new Bitboard(value);
    }

    /**
     * Set the bit at position `position` to the value `value`
     *
     * @param position The position to set
     * @param value The value to set at the position
     */
    public void set(Position position, boolean value) {
        long index = position.index();
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

    /**
     * Checks whether the bitboard is empty or not
     *
     * @return true if the bitboard is empty, false otherwise
     */
    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
