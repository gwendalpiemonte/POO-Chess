package engine;

/**
 * Represents a position on a chess board.
 * <br>
 * The positions are zero-based.
 *
 * @param file The given file
 * @param rank The given rank
 */
public record Position(int file, int rank) {
    public Position(Position position) {
        this(position.file, position.rank);
    }

    public static int charCoordinateToIndex(char c) {
        c = Character.toLowerCase(c);
        assert (c >= 'a' && c < 'i');
        return c - 'a';
    }

    public static int intCoordinateToIndex(char c) {
        assert (c >= '1' && c <= '9');
        return c - '1';
    }

    /**
     * Create a new position from a given string input in the format : `c7`
     *
     * @param input The given square of the board
     * @return The position from the given input
     */
    public static Position fromString(String input) {
        return new Position(
                charCoordinateToIndex(input.charAt(0)),
                intCoordinateToIndex(input.charAt(1))
        );
    }

    public Position add(int offset) {
        return Position.fromIndex(index() + offset);
    }

    /**
     * Returns the index of a given position (in 12x12, to ensure )
     *
     * @return The index of the position
     */
    public int index() {
        return (file() + 2) + ((rank() + 2) * 12);
    }

    public int indexUnsafe() {
        return file() + rank() * 8;
    }


    public static Position fromIndex(int index) {
        // The -2 are here to convert from 12x12 (safe) indexes, to 8x8 (unsafe) indexes
        return new Position(
                (index % 12) - 2,
                (index / 12) - 2
        );
    }

    /**
     * Checks if the position is within the bounds of a chess board
     *
     * @return true if within bounds, false otherwise
     */
    public boolean isWithinBounds() {
        return 0 <= rank && rank < 8
                && 0 <= file && file < 8;
    }
}
