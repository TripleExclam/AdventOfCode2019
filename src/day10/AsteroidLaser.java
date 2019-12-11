package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AsteroidLaser {

    private int width;
    private int height;
    private List<SpaceObject> spaceObjects;
    private SpaceObject baseLocation;
    private HashMap<Double, SpaceObject> angles;
    private int asteroidsNuked;

    public AsteroidLaser() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(AsteroidDetector.FILE));
        String line;

        asteroidsNuked = 0;
        spaceObjects = new ArrayList<>();
        height = 0;
        while ((line = reader.readLine()) != null) {

            width = line.length();

            for (int i = 0; i < width; i++) {
                spaceObjects.add(new SpaceObject(i, height, line.charAt(i)));
            }

            height++;
        }

        AsteroidDetector astDet = new AsteroidDetector();
        astDet.findBestBase();
        baseLocation = astDet.getBaseLocation();
        baseLocation.setType('X');
        spaceObjects.set(getIndex(baseLocation.getX(), baseLocation.getY()), baseLocation);
        System.out.println(dumpField());
        decideVisitingOrder();
    }

    public void decideVisitingOrder() {
        angles = new HashMap<>();
        double angle;
        for (int i = 0; i < spaceObjects.size(); i++) {
            if (spaceObjects.get(i).isAsteroid()) {
                angle = computeAngle(spaceObjects.get(i));
                if (!angles.containsKey(angle) || getDistance(spaceObjects.get(i)) < getDistance(angles.get(angle))) {
                    angles.put(computeAngle(spaceObjects.get(i)), spaceObjects.get(i));
                }
            }
        }
        List<Double> angleSort = new ArrayList<>(angles.keySet());
        angleSort.sort(Comparator.comparing(Double::doubleValue));

        angles.get(angleSort.get(0)).setType('1');
        angles.get(angleSort.get(1)).setType('2');
        angles.get(angleSort.get(2)).setType('3');
        angles.get(angleSort.get(3)).setType('4');
        System.out.println(dumpField());

        System.out.println(angles.get(angleSort.get(198))); // Hence 199 - 1 as first point skipped.
    }

    private double getDistance(SpaceObject spaceObject) {
        return Math.pow(spaceObject.getY() - baseLocation.getY(), 2)
                + Math.pow(spaceObject.getX() - baseLocation.getX(), 2);
    }

    private Double computeAngle(SpaceObject spaceObject) {
        // Translated to make pi/2 the first point.
        return Math.atan2(-(spaceObject.getX() - baseLocation.getX()),
                spaceObject.getY() - baseLocation.getY());
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

    private String dumpField() {
        StringBuilder builder = new StringBuilder();
        for (SpaceObject obj : spaceObjects) {
            builder.append(obj.getType());

            if (obj.getX() == width - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        AsteroidLaser laser = new AsteroidLaser();
    }
}
