package engine;

/**
 * Represents a position on the board
 *
 * @param file The given file
 * @param rank The given rank
 */
public record Position(int file, int rank) {
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

    /**
     * Returns the index of a given position
     *
     * @return The index of the position
     */
    public int index() {
        return file() + rank() * 8;
    }


    public static Position fromIndex(int index) {
        assert isIndexWithinBounds(index);

        return new Position(
                index % 8,
                index / 8
        );
    }

    public static boolean isIndexWithinBounds(int index) {
        return index < 64 && index >= 0;
    }
}
