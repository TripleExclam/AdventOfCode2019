package day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ImprovedComputer {
    private Map<String, BigInteger> opCodes;
    private BigInteger input;
    private BigInteger output;
    private int executionPoint;
    private boolean running;
    private BigInteger relativeBase;

    public ImprovedComputer(int input, String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader(fileName));

        String[] lines = reader.readLine().split(",");

        opCodes = new HashMap<>(lines.length);
        for (int i = 0; i < lines.length; i++) {
            opCodes.put(String.valueOf(i), new BigInteger(lines[i]));
        }

        this.input = new BigInteger(String.valueOf(input));
        this.executionPoint = 0;
        this.running = true;
        this.relativeBase = new BigInteger("0");
//
//        processInstructions();
    }

    public boolean running() {
        return running;
    }

    public int verifyInstruction(BigInteger instruction) {
        int operation;
        if (instruction == null) {
            return -1;
        }
        String mode = "0000" + instruction;

        operation = Character.getNumericValue(mode.charAt(mode.length() - 1));
        int secondDigit = Character.getNumericValue(mode.charAt(mode.length() - 2));

        if (operation == 0 || secondDigit != 0) {
            return -1;
        }

        return operation;
    }

    public void setInput(BigInteger input) {
        this.input = input;
    }

    public BigInteger getParameter(String opCode, int index) {
        String instruction = "00000" + opCodes.get(opCode);
        int mode = Character.getNumericValue(
                instruction.charAt(instruction.length() - 2 - index));

        opCode = new BigInteger(opCode).add(new BigInteger(String.valueOf(index))).toString();
        BigInteger valueFound = null;

        switch (mode) {
            case 0:
                valueFound = opCodes.get(opCode);
                break;
            case 1:
                valueFound = new BigInteger(opCode);
                break;
            case 2:
                valueFound = opCodes.get(opCode).add(relativeBase);
                break;
        }

        return (valueFound == null) ? new BigInteger("0") : valueFound;
    }

    public void processInstructions() {
        int instruction;
        BigInteger paramOne;
        BigInteger paramTwo;
        BigInteger destination;
        int jump = 0;

        for (int i = executionPoint; i < opCodes.size(); i += jump) {
            String index = String.valueOf(i);
            instruction = verifyInstruction(opCodes.get(index));

            if (instruction == -1) {
                running = false;
                return;
            }

            paramOne = getParameter(index, 1);
            paramTwo = getParameter(index, 2);
            destination = getParameter(index, 3);


            if (instruction != 3) {
                paramOne = opCodes.get(paramOne.toString());
                paramTwo = opCodes.get(paramTwo.toString());
                if (paramOne == null) {
                    paramOne = new BigInteger("0");
                }
                if (paramTwo == null) {
                    paramTwo = new BigInteger("0");
                }
            }

            switch (instruction) {
                case 1:
                case 2:
                    jump = 4;
                    if (instruction == 1) {
                        opCodes.put(destination.toString(), paramOne.add(paramTwo));
                    } else {
                        opCodes.put(destination.toString(), paramOne.multiply(paramTwo));
                    }
                    break;
                case 3:
                    jump = 2;
                    opCodes.put(paramOne.toString(), input);
                    break;
                case 4:
                    output = paramOne;
                    executionPoint = i + 2;
                    // System.out.println("Saved output: " + output);
                    return;
                case 5:
                    jump = 3;
                    if (!paramOne.equals(new BigInteger("0"))) {
                        i = paramTwo.intValue();
                        jump = 0;
                    }
                    break;
                case 6:
                    jump = 3;
                    if (paramOne.equals(new BigInteger("0"))) {
                        i = paramTwo.intValue();
                        jump = 0;
                    }
                    break;
                case 7:
                    opCodes.put(destination.toString(), (paramOne.compareTo(paramTwo) < 0) ?
                            new BigInteger("1") : new BigInteger("0"));
                    jump = 4;
                    break;
                case 8:
                    opCodes.put(destination.toString(), (paramOne.equals(paramTwo)) ?
                            new BigInteger("1") : new BigInteger("0"));
                    jump = 4;
                    break;
                case 9:
                    relativeBase = relativeBase.add(paramOne);
                    jump = 2;
                    break;
                default:
                    running = false;
                    return;
            }
        }
    }


    public String getOutput() {
        return output.toString();
    }

    public static void main(String[] args) throws IOException {
        ImprovedComputer comp = new ImprovedComputer(1, "src/day9/Boost.txt");
        while (comp.running()) {
            System.out.println(comp.getOutput());
            comp.processInstructions();
        }
    }
}
