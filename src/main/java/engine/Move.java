package engine;

public record Move(Position from, Position to) {
    public Move(Move other) {
        this(new Position(other.from), new Position(other.to));
    }
}
