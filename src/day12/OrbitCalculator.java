package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import static java.lang.Math.abs;

public class OrbitCalculator {
    private Position[] moons;
    private Position[] moonSpeeds;
    private int energy;

    public OrbitCalculator(String fileName) throws IOException {
        moons = new Position[4];
        moonSpeeds = new Position[4];
        energy = 0;

        BufferedReader reader = new BufferedReader(
                new FileReader(fileName));
        String line;
        String[] positions;

        for (int i = 0; i < 4; i++) {
            line = reader.readLine();
            line = line.substring(1, line.length() - 1);
            positions = line.split(",");

            int x = Integer.parseInt(positions[0].split("=")[1]);
            int y = Integer.parseInt(positions[1].split("=")[1]);
            int z = Integer.parseInt(positions[2].split("=")[1]);

            moons[i] = new Position(x, y, z);
            moonSpeeds[i] = new Position();
        }
    }

    private void dumpSystem() {
        for (int l = 0; l < 4; l++) {
            System.out.print(moons[l] + " Velocity: ");
            System.out.println(moonSpeeds[l]);
        }
        System.out.println();
    }

    public void simulateSolarSystem(int steps) {
        for (int i = 0; i < steps; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = j + 1; k < 4; k++) {
                    moonSpeeds[j].calculateCoords(moons[j], moons[k]);
                    moonSpeeds[k].calculateCoords(moons[k], moons[j]);
                }
            }

            for (int l = 0; l < 4; l++) {
                moons[l].add(moonSpeeds[l]);
            }
        }
    }

    public Position[] getMoons() {
        return moons;
    }

    public Position[] getMoonSpeeds() {
        return moonSpeeds;
    }

    public boolean checkMatching(Position[] moons, Position[] moonSpeeds, int coord) {
        for (int i = 0; i < 4; i++) {
            if (!moons[i].equals(this.moons[i], coord)
                    || !moonSpeeds[i].equals(this.moonSpeeds[i], coord)) {
                return false;
            }
        }
        return true;
    }

    public int getEnergy() {
        for (int i = 0; i < 4; i++) {
            energy += (abs(moons[i].getX()) + abs(moons[i].getY()) + abs(moons[i].getZ()))
                    * (abs(moonSpeeds[i].getX()) + abs(moonSpeeds[i].getY()) + abs(moonSpeeds[i].getZ()));
        }
        return energy;
    }

    public static BigInteger GCD(BigInteger a, BigInteger b) {
        if (b.equals(new BigInteger("0"))) {
            return a;
        }
        return GCD(b, a.mod(b));
    }

    public static BigInteger LCM(BigInteger a, BigInteger b) {
        return a.multiply(b.divide(GCD(a, b)));
    }

    public static void main(String[] args) throws IOException {
        int steps[] = new int[3];
        OrbitCalculator orbiter = new OrbitCalculator("src/day12/day12Input.txt");
        OrbitCalculator secondOrbiter = new OrbitCalculator("src/day12/day12Input.txt");
        secondOrbiter.simulateSolarSystem(1);

        orbiter.dumpSystem();
        while (!orbiter.checkMatching(secondOrbiter.getMoons(), secondOrbiter.getMoonSpeeds(), 0)) {
            secondOrbiter.simulateSolarSystem(1);
            steps[0]++;
        }
        secondOrbiter.dumpSystem();

        secondOrbiter = new OrbitCalculator("src/day12/day12Input.txt");
        secondOrbiter.simulateSolarSystem(1);

        while (!orbiter.checkMatching(secondOrbiter.getMoons(), secondOrbiter.getMoonSpeeds(), 1)) {
            secondOrbiter.simulateSolarSystem(1);
            steps[1]++;
        }
        secondOrbiter.dumpSystem();

        secondOrbiter = new OrbitCalculator("src/day12/day12Input.txt");
        secondOrbiter.simulateSolarSystem(1);

        while (!orbiter.checkMatching(secondOrbiter.getMoons(), secondOrbiter.getMoonSpeeds(), 2)) {
            secondOrbiter.simulateSolarSystem(1);
            steps[2]++;
        }
        secondOrbiter.dumpSystem();

        System.out.println(steps[0]);
        System.out.println(steps[1]);
        System.out.println(steps[2]);

        BigInteger step = LCM(LCM(new BigInteger(String.valueOf(steps[0] + 1)),
                new BigInteger(String.valueOf(steps[1] + 1))),
                new BigInteger(String.valueOf(steps[2] + 1)));

        System.out.print(step);
    }

}
