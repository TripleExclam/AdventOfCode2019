package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsteroidDetector {
    public static final String FILE = "src/day10/asteroidField.txt";

    private int width;
    private int height;
    private List<SpaceObject> spaceObjects;
    private List<SpaceObject> currentField;
    private SpaceObject currentRoid;
    private SpaceObject baseLocation;

    public AsteroidDetector() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILE));
        String line;

        spaceObjects = new ArrayList<>();
        height = 0;
        while ((line = reader.readLine()) != null) {

            width = line.length();

            for (int i = 0; i < width; i++) {
                spaceObjects.add(new SpaceObject(i, height, line.charAt(i)));
            }

            height++;
        }
    }

    public boolean checkBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public int getIndex(int x, int y) {
        if (!checkBounds(x, y)) {
            return -1;
        }
        return y * height + x;
    }

    public int countAsteroids(List<SpaceObject> field) {
        int count = 0;
        for (SpaceObject block : field) {
            if (block.isAsteroid()) {
                count++;
            }
        }
        return count - 1;
    }

    public int findBestBase() {
        int maxAst = Integer.MIN_VALUE;
        int currentAst = Integer.MIN_VALUE;

        for (SpaceObject ast : spaceObjects) {
            if (ast.isAsteroid()) {
                currentAst = computeVision(ast);
            }

            if (currentAst > maxAst) {
                baseLocation = ast;
                maxAst = currentAst;
                // System.out.println(dumpField());
            }
        }

        return maxAst;
    }

    public SpaceObject getBaseLocation() {
        return baseLocation;
    }

    private String dumpField() {
        StringBuilder builder = new StringBuilder();
        for (SpaceObject obj : currentField) {
            if (obj.getY() == currentRoid.getY()
                    && obj.getX() == currentRoid.getX()) {
                builder.append("O");
            } else {
                builder.append(obj.getType());
            }
            if (obj.getX() == width - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private int computeVision(SpaceObject ast) {
        currentField = new ArrayList<>(spaceObjects);
        currentRoid = ast;

        int maxDim = Math.max(height, width);
        int[] point = new int[2];

        for (int i = 1; i < maxDim; i++) {
            point[0] = (i < width) ? i : width - 1;
            point[1] = (i < height) ? i : height - 1;

            checkSides(point, 0);
            checkSides(point, 1);
            checkSides(point, 2);
            checkSides(point, 3);
        }

        return countAsteroids(currentField);
    }

    private void checkSides(int[] sideLen, int state) {
        int[] x = new int[] {-sideLen[0], sideLen[0]};
        int[] y = new int[] {-sideLen[1], sideLen[1]};
        int index;
        if (state == 0) {
            y[0] = sideLen[1]; // Top edge
        } else if (state == 1) {
            x[0] = sideLen[0]; // Right edge
        } else if (state == 2) {
            y[1] = -sideLen[1]; // Bottom edge
        } else {
            x[1] = -sideLen[0]; // Left edge
        }

        for (int i = x[0]; i <= x[1]; i++) {
            for (int j = y[0]; j <= y[1]; j++) {
                index = getIndex(i + currentRoid.getX(), j + currentRoid.getY());
                if (index != -1 && currentField.get(index).isAsteroid()) {
                    removeLinearFriends(new int[] {i, j});
                }
            }
        }
    }

    private void removeLinearFriends(int[] steps) {
        int x = currentRoid.getX() + steps[0];
        int y = currentRoid.getY() + steps[1];

        refineSteps(steps);

        x += steps[0];
        y += steps[1];

        while (getIndex(x, y) != -1) {
            if (currentField.get(getIndex(x, y)).isAsteroid()) {
                currentField.set(getIndex(x, y), new SpaceObject(x, y, 'D'));
                // System.out.println(calculateGradient(x, y));
            }
            x += steps[0];
            y += steps[1];
        }
    }

    public static int GCD(int a, int b) {
        if (b==0) {
            return a;
        }
        return GCD(b,a%b);
    }

    private void refineSteps(int[] steps) {
        int commonDivisor = GCD(steps[0], steps[1]);

        steps[0] /= Math.abs(commonDivisor);
        steps[1] /= Math.abs(commonDivisor);
    }

    public static void main(String[] args) throws IOException {
        int astDet = new AsteroidDetector().findBestBase();
        System.out.println(astDet);
    }


}
