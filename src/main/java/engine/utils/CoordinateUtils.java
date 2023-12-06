package engine.utils;

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
}
