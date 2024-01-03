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
}
