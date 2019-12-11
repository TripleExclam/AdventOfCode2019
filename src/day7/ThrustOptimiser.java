package day7;

import day5.OpCodeComputer;

import java.io.IOException;

public class ThrustOptimiser {
    private static final int phaseRange = 4;
    private static final String fileName = "src/day7/LoopingOpCodes.txt";

    public static int sumList(int[] toSum, int stop) {
        int sum = 0;
        for (int i = 0; i < stop; i++) {
            sum += toSum[i];
        }
        return sum;
    }

    public static void incrementPhases(int[] phases) {
        phases[0] = (phases[0] + 1) % (phaseRange + 1);

        for (int i = 1; i < phases.length; i++) {
            if (sumList(phases, i) == i * 4) {
                phases[i] = (phases[i] + 1) % (phaseRange + 1);
            }
        }
        for (int i = 0; i < phases.length; i++) {
            for (int j = 0; j < phases.length; j++) {
                if (i != j && phases[i] == phases[j]) {
                    incrementPhases(phases);
                }
            }
        }
    }

    private static void dumpInfo(int maxThrust, int[] maxPhases) {
        for (int phase : maxPhases) {
            System.out.print((phase + 5) + ", ");
        }

        System.out.println();
        System.out.println("Max Thrust = " + maxThrust);
    }

    public static void main(String[] args) throws IOException {
        int maxThrust = Integer.MIN_VALUE;
        int[] phases = new int[] {4, 3, 2, 1, 0};
        int[] maxPhases = new int[0];
        OpCodeComputer[] compute = new OpCodeComputer[5];

        for (int i = 0; i < 120; i++) {
            compute[0] = new OpCodeComputer(phases[0] + 5, 0, fileName);
            
            for (int j = 1; j < phases.length; j++) {
                compute[j] = new OpCodeComputer(phases[j] + 5,
                        compute[j - 1].getOutput(), fileName);
            }

            int j = 0;
            while (compute[j].running()) {
                compute[j].setInput((j == 0) ? compute[4].getOutput() : compute[j - 1].getOutput());
                compute[j].processInstructions();
                j = (j + 1) % 5;
                System.out.println("Computer " + j + " output " + compute[j].getOutput());
            }


            if (compute[4].getOutput() > maxThrust) {
                maxThrust = compute[4].getOutput();
                maxPhases = phases.clone();
            }

            incrementPhases(phases);
        }
        dumpInfo(maxThrust, maxPhases);
    }

}
