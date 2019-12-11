package day2;

import day5.OpCodeComputer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpCodeExecuter {
    private int[] opCodes;

    public OpCodeExecuter() throws IOException {
        BufferedReader reader = new BufferedReader(
                new FileReader("src/day2/opCodes.txt"));

        String[] lines = reader.readLine().split(",");
        opCodes = new int[lines.length];

        for (int i = 0; i < lines.length; i++) {
            opCodes[i] = Integer.parseInt(lines[i]);
        }

    }

    public OpCodeExecuter(int mem1, int mem2) throws IOException {
        this();
        opCodes[1] = mem1;
        opCodes[2] = mem2;
    }

    public void processInstructions() {
        int instruction;
        int memoryOne;
        int memoryTwo;
        int destination;

        for (int i = 0; i < opCodes.length; i += 4) {
            instruction = opCodes[i];
            memoryOne = opCodes[i + 1];
            memoryTwo = opCodes[i + 2];
            destination = opCodes[i + 3];

            switch (instruction) {
                case 1:
                    opCodes[destination] = opCodes[memoryOne] + opCodes[memoryTwo];
                    break;
                case 2:
                    opCodes[destination] = opCodes[memoryOne] * opCodes[memoryTwo];
                    break;
                default:
                    i = opCodes.length;
            }
        }

    }

    public int[] getOpCodes() {
        return opCodes;
    }

    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 99; i++) {
            for (int j = 0; j < 99; j++) {
                OpCodeExecuter decoder = new OpCodeExecuter(i, j);
                if (decoder.getOpCodes()[0] == 19690720) {
                    System.out.println(i + " " + j);
                }
            }
        }
    }
}
