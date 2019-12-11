package day5;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpCodeComputer {
    private int[] opCodes;
    private int input;
    private int phaseSetting;
    private int output;
    private int executionPoint;
    private int relativeBase;

    public OpCodeComputer(int phaseSetting, int input, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(fileName));

        String[] lines = reader.readLine().split(",");
        opCodes = new int[lines.length];

        for (int i = 0; i < lines.length; i++) {
            opCodes[i] = Integer.parseInt(lines[i]);
        }
        this.input = input;
        this.phaseSetting = phaseSetting;
        this.executionPoint = 0;

        processInstructions();
    }

    public void setInput(int input) {
        this.input = input;
        this.phaseSetting = input;
    }

    public boolean running() {
        return executionPoint < opCodes.length;
    }

    public void processInstructions() {
        String mode;
        int instruction;
        int paramOne;
        int paramTwo;
        int destination;
        int jump = 0;

        for (int i = executionPoint; i < opCodes.length; i += jump) {
            mode = String.valueOf(opCodes[i]);
            mode = "0000" + mode;
            instruction = Character.getNumericValue(mode.charAt(mode.length() - 1));
//            System.out.println(instruction);
            if (instruction < 1 || instruction > 8) {
                executionPoint = opCodes.length;
                break;
            }
            paramOne = (mode.charAt(mode.length() - 3) == '0')
                    ? opCodes[i + 1] : i + 1;
            paramTwo = (mode.charAt(mode.length() - 4) == '0')
                    ? opCodes[i + 2] : i + 2;
            switch (instruction) {
                case 1:
                case 2:
                    paramOne = opCodes[paramOne];
                    paramTwo = opCodes[paramTwo];
                    destination = opCodes[i + 3];
//                    System.out.println(paramOne + " " + paramTwo);
                    opCodes[destination] = (instruction == 1)
                            ? paramOne + paramTwo : paramOne * paramTwo;
//                    System.out.println("Put value at: "
//                            + destination + " as " + opCodes[destination]);
                    jump = 4;
                    break;
                case 3:
                    opCodes[paramOne] = phaseSetting;
                    phaseSetting = input;
//                    System.out.println("Written input");
                    jump = 2;
                    break;
                case 4:
                    output = opCodes[paramOne];
                    executionPoint = i + 2;
                    // System.out.println("Saved output: " + output);
                    return;
                case 5:
                    System.out.println(opCodes[paramOne] + " check value is 0");
                    jump = 3;
                    if (opCodes[paramOne] != 0) {
                        System.out.println("Successful jump to: " + opCodes[paramTwo]);
                        i = opCodes[paramTwo];
                        jump = 0;
                    }
                    break;
                case 6:
                    jump = 3;
                    if (opCodes[paramOne] == 0) {
                        i = opCodes[paramTwo];
                        jump = 0;
                    }
                    break;
                case 7:
                    destination = opCodes[i + 3];
                    opCodes[destination] = (opCodes[paramOne]
                            < opCodes[paramTwo]) ? 1 : 0;
                    jump = 4;
//                    System.out.println("Put value at: "
//                            + destination + " as " + opCodes[destination]);
                    break;
                case 8:
                    destination = opCodes[i + 3];
                    opCodes[destination] = (opCodes[paramOne]
                            == opCodes[paramTwo]) ? 1 : 0;
//                    System.out.println("Put value at: "
//                            + destination + " as " + opCodes[destination]);
                    jump = 4;
                    break;
                case 9:
                    relativeBase = opCodes[paramOne];
                    jump = 2;
                    break;
                default:
                    executionPoint = opCodes.length;
                    return;
            }
//            for (int k : opCodes) {
//                System.out.print(k + ", ");
//            }
//            System.out.println();
        }
    }

    public int[] getOpCodes() {
        return opCodes;
    }

    public int getOutput() {
        return output;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(
                new OpCodeComputer(4,4, "src/day5/instructions.txt").getOutput());
    }
}
