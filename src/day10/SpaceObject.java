package day10;

public class SpaceObject {
    private int x;
    private int y;
    private char type;

    public SpaceObject(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public boolean isAsteroid() {
        return type == '#';
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "located at [" + x + ", " + y + "] with type " + type;
    }
}
