package engine.utils;

import engine.Position;

public class CoordinateUtils {
    public static int charCoordinateToIndex(char c) {
        c = Character.toLowerCase(c);
        assert (c >= 'a' && c < 'i');
        return c - 'a';
    }

    public static int intCoordinateToIndex(char c) {
        assert (c >= '1' && c <= '9');
        return c - '1';
    }

    public static Position fromString(String input) {
        return new Position(
                charCoordinateToIndex(input.charAt(0)),
                intCoordinateToIndex(input.charAt(1))
        );
    }
}
