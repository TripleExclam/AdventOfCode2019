package day12;

public class Position {
    private int x;
    private int y;
    private int z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position() {
        this(0, 0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void add(Position opposite) {
        x += opposite.getX();
        y += opposite.getY();
        z += opposite.getZ();
    }

    public void calculateCoords(Position current, Position toCompare) {
        x += Integer.compare(toCompare.getX(), current.getX());
        y += Integer.compare(toCompare.getY(), current.getY());
        z += Integer.compare(toCompare.getZ(), current.getZ());
    }




    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position) {
            Position comparePos = (Position) obj;
            return x == comparePos.getX() && y == comparePos.getY() && z == comparePos.getZ();
        }
        return false;
    }

    public boolean equals(Object obj, int pos) {
        if (obj instanceof Position) {
            Position comparePos = (Position) obj;
            if (pos == 0) {
                return x == comparePos.getX();
            } else if (pos == 1) {
                return y == comparePos.getY();
            } else {
                return z == comparePos.getZ();
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return x * 13 + y * 17 + z * 11;
    }
}
