package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PathFinder {
    private int shortest;


    public PathFinder() throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader("src/day3/ClosestWire.txt"));

        String[] setOne = reader.readLine().split(",");
        String[] setTwo = reader.readLine().split(",");

        shortest = populateDirection(setOne, setTwo);
    }

    public int getShortest() {
        return shortest;
    }

    private int populateDirection(String[] instructionsOne, String[] instructionsTwo) {
        int minDistance = Integer.MAX_VALUE;

        Line[] linesOne = getLines(instructionsOne);
        Line[] linesTwo = getLines(instructionsTwo);

        for (Line segOne : linesOne) {
            for (Line segTwo : linesTwo) {
                if (segOne.checkIntersecting(segTwo) || segOne.checkOverlapping(segTwo)) {
                    minDistance = Math.min(minDistance,
                            segOne.findSmallestIntersect(segTwo));
                    // System.out.println(minDistance);
                }
            }

        }
        return minDistance;
    }

    private Line[] getLines(String[] instructions) {
        Line[] lines = new Line[instructions.length];
        int[] oldPoint;
        int[] point = new int[2];
        int steps = 0;
        int nextSteps = 0;

        for (int i = 0; i < instructions.length; i++) {
            steps += nextSteps;
            oldPoint = point.clone();
            nextSteps = populatePoints(instructions[i], point);
            lines[i] = new Line(oldPoint, point, steps);

        }

        return lines;
    }

    private int populatePoints(String direction, int[] point) {
        int steps = Integer.parseInt(direction.substring(1));

        switch (direction.charAt(0)) {
            case 'U':
                point[1] += steps;
                break;
            case 'D':
                point[1] -= steps;
                break;
            case 'L':
                point[0] -= steps;
                break;
            case 'R':
                point[0] += steps;
                break;
        }

        return steps;
    }



    public static void main(String[] args) throws IOException {
        System.out.println(new PathFinder().getShortest());
    }

    private class Line {
        private int[] start;
        private int[] end;
        private int steps;
        private int direction;

        public Line(int[] start, int[] end, int steps) {
            this(start, end);
            this.steps = steps;
        }

        public Line(int[] start, int[] end) {
            if (start[0] > end[0] || start[1] > end[1]) {
                this.start = end.clone();
                this.end = start.clone();
                direction = -1;
            } else {
                this.start = start.clone();
                this.end = end.clone();
                direction = 1;
            }
        }

        public int[] getDirection() {
            if (start[0] == end[0]) {
                return new int[] {1, (start[1] < end[1]) ? 1 : -1};
            }
            return new int[] {(start[0] < end[0]) ? 1 : -1, 1};
        }

        public int[] getVector() {
            return new int[] {end[0] - start[0],
                    end[1] - start[1]};
        }

        public int[] getStart() {
            return start;
        }

        public int[] getEnd() {
            return end;
        }

        public boolean checkIntersecting(Line other) {
            int[] otherStart = other.getStart();
            int[] otherEnd = other.getEnd();

            int dotProduct = getVector()[0] * other.getVector()[0]
                    + getVector()[1] * other.getVector()[1];

            return dotProduct == 0 && ((start[0] == end[0] && otherStart[1] == otherEnd[1]
                    && start[0] >= otherStart[0] && start[0] <= otherEnd[0]
                    && otherStart[1] >= start[1] && otherStart[1] <= end[1])
                    || (start[1] == end[1] && otherStart[0] == otherEnd[0]
                    && start[1] >= otherStart[1] && start[1] <= otherEnd[1]
                    && otherStart[0] >= start[0] && otherStart[0] <= end[0]));
        }

        public int getSteps() {
            return steps;
        }

        public boolean checkOverlapping(Line other) {
            int[] otherStart = other.getStart();
            int[] otherEnd = other.getEnd();

            return  (start[0] == otherStart[0] && end[0] == otherEnd[0])
                    || (start[1] == otherStart[1] && end[1] == otherEnd[1]);
        }

        private int findSmallestIntersect(Line other) {
            int[] otherStart = other.getStart();
            int[] otherEnd = other.getEnd();
            int steps = getSteps() + other.getSteps();
            if (steps == 0) {
                return Integer.MAX_VALUE;
            }
            System.out.println("Found intersect");
            System.out.println(steps);
            System.out.println(this);
            System.out.println(other);


            return Integer.MAX_VALUE;
        }

        @Override
        public String toString() {
            return "[" + start[0] + ", " + start[1] + "]"
                    + "[" + end[0] + ", " + end[1] + "]"
                    + "Direction " + direction;
        }
    }
}
