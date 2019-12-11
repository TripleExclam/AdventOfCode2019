package day11;

public enum Direction {
    LEFT(-1, 0), RIGHT(1, 0), UP(0, 1), DOWN(0, -1);
    private Direction left;
    private Direction right;
    private int xOffset;
    private int yOffset;

    Direction(int x, int y) {
        xOffset = x;
        yOffset = y;
    }

    static {
        LEFT.left = DOWN;
        RIGHT.left = UP;
        UP.left = LEFT;
        DOWN.left = RIGHT;
        LEFT.right = UP;
        RIGHT.right = DOWN;
        UP.right = RIGHT;
        DOWN.right = LEFT;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public Direction turnLeft() {
        return left;
    }

    public Direction turnRight() {
        return right;
    }
}
