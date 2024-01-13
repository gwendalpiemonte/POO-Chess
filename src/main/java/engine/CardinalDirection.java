package engine;

import java.util.List;

public enum CardinalDirection {
    // OFFSETS ARE IN 12x12
    NORTH_WEST(+11),
    NORTH(+12),
    NORTH_EAST(+13),
    EAST(+1),
    SOUTH_EAST(-11),
    SOUTH(-12),
    SOUTH_WEST(-13),
    WEST(-1);


    private final int offset;

    public static final List<CardinalDirection> DIAGONALS = List.of(
            NORTH_WEST,
            NORTH_EAST,
            SOUTH_EAST,
            SOUTH_WEST
    );

    public static final List<CardinalDirection> ORTHOGONALS = List.of(
            NORTH,
            SOUTH,
            EAST,
            WEST
    );

    CardinalDirection(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public Position apply(Position position) {
        return position.add(offset);
    }
}
