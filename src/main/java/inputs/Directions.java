package inputs;

public enum Directions {
    DOWN (0),
    UP (1),
    LEFT (2),
    RIGHT (3),
    ALL(4);
    int direction;

    Directions(int direction) {
        this.direction = direction;
    }
}
