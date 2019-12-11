package day11;

import day9.ImprovedComputer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotPainter {
    private static final String FILE = "src/day11/day11Input.txt";
    private Direction direction = Direction.UP;
    private int tileCount = 0;

    private static final char BLACK = '.';
    private static final char WHITE = '#';

    Map<Integer, Character> board = new HashMap<>();
    char[][] charBoard = new char[150][150];


    public RobotPainter() throws IOException {

        ImprovedComputer comp = new ImprovedComputer(0, FILE);
//        for (int i = 0; i < charBoard.length; i++) {
//            for (int j = 0; j < charBoard.length; j++) {
//                charBoard[i][j] = BLACK;
//            }
//
//        }

        int[] currentPosition = new int[] {75, 75};
        // charBoard[75][75] = WHITE;

        while (comp.running()) {
            comp.setInput(getTile(currentPosition));
            comp.processInstructions();
            setTile(Integer.parseInt(comp.getOutput()), currentPosition);

            comp.processInstructions();
            move(Integer.parseInt(comp.getOutput()), currentPosition);
        }
        dumpImage();
    }

    public int getTileCount() {
        return tileCount;
    }

    private void move(int direction, int[] currentPosition) {
        if (direction == 0) {
            this.direction = this.direction.turnLeft();
        } else {
            this.direction = this.direction.turnRight();
        }
        currentPosition[0] = currentPosition[0] + this.direction.getxOffset();
        currentPosition[1] = currentPosition[1] + this.direction.getyOffset();
    }

    private void setTile(int tile, int[] currentPosition) {
        int hash = currentPosition[0] + currentPosition[1] * 100;

        if (charBoard[currentPosition[0]][currentPosition[1]] != BLACK
                && charBoard[currentPosition[0]][currentPosition[1]] != WHITE) {
            tileCount++;
        }

        if (tile == 0) {
            board.put(hash, BLACK);
            charBoard[currentPosition[0]][currentPosition[1]] = BLACK;
        } else {
            board.put(hash, WHITE);
            charBoard[currentPosition[0]][currentPosition[1]] = WHITE;
        }
    }

    private BigInteger getTile(int[] currentPosition) {


        if (charBoard[currentPosition[0]][currentPosition[1]] == BLACK
                || charBoard[currentPosition[0]][currentPosition[1]] == WHITE) {
            return (charBoard[currentPosition[0]][currentPosition[1]] == BLACK)
                    ? new BigInteger("0") : new BigInteger("1");
        }

        return new BigInteger("0");
    }

    public void dumpImage() {
        for (int i = 0; i < charBoard.length; i++) {
            for (int j = 0; j < charBoard.length; j++) {
                System.out.print(charBoard[i][j]);
            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws IOException {
        RobotPainter answer = new RobotPainter();

        System.out.print(answer.getTileCount());
    }

}
