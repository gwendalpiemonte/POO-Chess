package engine.bitboard;

import engine.Position;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Bitboard {
    /**
     * Represents an 8x8 board where the LSB is in the bottom-left and the MSB is in the top-right.
     * <p>
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
     * @return The resulting bitboard
     */
    public static Bitboard single(Position position) {
        if (!position.isWithinBounds()) {
            return new Bitboard();
        }
        return new Bitboard(1L << position.indexUnsafe());
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
        long index = position.indexUnsafe();
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
     * @param value    The value to set at the position
     */
    public void set(Position position, boolean value) {
        long index = position.indexUnsafe();
        this.value = (this.value & ~(1L << index)) | ((value ? 1L : 0L) << index);
    }

    public boolean get(Position position) {
        return (this.value & (1L << position.indexUnsafe())) != 0L;
    }

    public Bitboard and(Bitboard board) {
        return new Bitboard(this.value & board.value);
    }

    public Bitboard or(Bitboard board) {
        return new Bitboard(this.value | board.value);
    }

    // Used for the Collectors, as a mutable item is required.
    private void mutableOr(Bitboard bitboard) {
        this.value |= bitboard.value;
    }

    public Bitboard xor(Bitboard board) {
        return new Bitboard(this.value ^ board.value);
    }

    public Bitboard shift(int direction) {
        if (direction > 0) {
            return new Bitboard(this.value << direction);
        } else {
            return new Bitboard(this.value >> -direction);
        }
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

    /**
     * Slide multiple times (create a trace of x cells long in the given offset)
     * <br/>
     * This is equivalent of doing x times the following operation: {@code board.or(board.slide(offset))}
     *
     * @param offset The slide to do on each operation
     * @param count  The number of times to slide
     * @return The resulting bitboard
     */
    public Bitboard cumulativeShift(int offset, int count) {
        Bitboard cloned = new Bitboard(this.value);

        for (int i = 0; i < count; ++i) {
            cloned.mutableOr(cloned.shift(offset));
        }

        return cloned;
    }

    public Stream<Position> stream() {
        Stream.Builder<Position> builder = Stream.builder();

        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Position position = new Position(file, rank);
                if (get(position)) {
                    builder.accept(position);
                }
            }
        }

        return builder.build();
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    public static Collector<Bitboard, Bitboard, Bitboard> collect() {
        return new Collector<>() {
            @Override
            public Supplier<Bitboard> supplier() {
                return Bitboard::new;
            }

            @Override
            public BiConsumer<Bitboard, Bitboard> accumulator() {
                return Bitboard::mutableOr;
            }

            @Override
            public BinaryOperator<Bitboard> combiner() {
                return Bitboard::or;
            }

            @Override
            public Function<Bitboard, Bitboard> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(
                        Characteristics.UNORDERED,
                        Characteristics.IDENTITY_FINISH
                );
            }
        };
    }


    public static Collector<Position, Bitboard, Bitboard> collectPositions() {
        return new Collector<>() {
            @Override
            public Supplier<Bitboard> supplier() {
                return Bitboard::new;
            }

            @Override
            public BiConsumer<Bitboard, Position> accumulator() {
                return (bitboard, position) -> bitboard.set(position, true);
            }

            @Override
            public BinaryOperator<Bitboard> combiner() {
                return Bitboard::or;
            }

            @Override
            public Function<Bitboard, Bitboard> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Set.of(
                        Characteristics.UNORDERED,
                        Characteristics.IDENTITY_FINISH
                );
            }
        };
    }
}
