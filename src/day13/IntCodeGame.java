package day13;

import day12.Position;
import day9.ImprovedComputer;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class IntCodeGame {
    public static final String FILE = "src/day13/day13Input.txt";

    private ImprovedComputer comp;
    private Map<Position, Integer> board;
    private Canvas canv;
    private Stage root;
    private Group rootGroup;
    private LinkedList<String> input;
    private int tick = 0;

    private Position previousBall = new Position(0, 0, 0);
    private Position previousPaddle = new Position(0, 0, 0);

    public IntCodeGame(Stage stage) throws IOException {
        root = stage;
        root.setTitle("Yeet");

        this.root = stage;

        // set the title
        root.setTitle("CSSE2002/7023 PacMan");

        // set the window size
        root.setWidth(1080);
        root.setHeight(720);
        // create the scene
        rootGroup = new Group();
        Scene rootScene = new Scene(rootGroup);
        root.setScene(rootScene);
        // enable the event handlers
        input = new LinkedList<>();

        // This grabs key presses and adds it to the queue
        rootScene.setOnKeyPressed(event -> {
            String code = event.getCode().toString();
            input.push(code);
        });

        canv = new Canvas();
        canv.setWidth(1000);
        canv.setHeight(1000);


        rootGroup.getChildren().addAll(canv);

        comp = new ImprovedComputer(0, FILE);
        board = new HashMap<>();

        clearCanvas();
        for (int i = 0; i < 1000; i++) {
            tickComputer();
        }

        run();
    }

    public void clearCanvas() {
        canv.getGraphicsContext2D().setFill(Color.WHITE);
        canv.getGraphicsContext2D().fillRect(0, 0, 1000, 1000);
    }

    public void tickComputer() {
        int xOffset;
        int yOffset;
        int tile;

        comp.processInstructions();
        xOffset = Integer.parseInt(comp.getOutput());
        comp.processInstructions();
        yOffset = Integer.parseInt(comp.getOutput());
        comp.processInstructions();
        tile = Integer.parseInt(comp.getOutput());

        if (xOffset == -1 && yOffset == 0) {
            System.out.println("Current score: " + tile);
            return;
        }

        board.put(new Position(xOffset, yOffset, 0), tile);

    }

    public void run() {
        new AnimationTimer() {

            public void handle(long currentNanoTime) {
                if (previousBall.getX() < previousPaddle.getX()) {
                    comp.setInput(new BigInteger("-1"));
                } else if (previousBall.getX() > previousPaddle.getX()) {
                    comp.setInput(new BigInteger("1"));
                } else {
                    comp.setInput(new BigInteger("0"));
                }
                tick++;
                if (tick % 1 == 0) {
                    tickComputer();
                }
                printBoard();
            }
        }.start();

        // Show the game screen
        this.root.show();
    }

    public void printBoard() {
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if (board.get(new Position(j, i, 0)) != null) {
                    drawBox(new Position(j, i, 0), board.get(new Position(j, i, 0)));
                }
            }

        }
    }

    private void drawBox(Position pos, int tile) {
        GraphicsContext context = canv.getGraphicsContext2D();
        int blockSize = 20;
        context.setStroke(Color.WHITE);
        context.setFill(Color.WHITE);

        switch (tile) {
            case 0:
                return;
            case 1:
                context.setStroke(Color.BLACK);
                context.setFill(Color.BLACK);
                break;
            case 2:
                context.setStroke(Color.BLUE);
                context.setFill(Color.BLUE);
                break;
            case 3:
                context.fillRect(previousPaddle.getX() * blockSize, previousPaddle.getY() * blockSize,
                        blockSize,
                        blockSize);
                previousPaddle = pos;
                context.setStroke(Color.RED);
                context.setFill(Color.RED);
                break;
            case 4:
                context.fillRect(previousBall.getX() * blockSize, previousBall.getY() * blockSize, blockSize,
                        blockSize);
                previousBall = pos;
                context.setStroke(Color.GREEN);
                context.setFill(Color.GREEN);
                break;
        }
        context.fillRect(pos.getX() * blockSize, pos.getY() * blockSize, blockSize, blockSize);
    }

    public int getBlocks() {
        int count  = 0;
        for (int i : board.values()) {
            if (i == 2) {
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) throws IOException {
        //IntCodeGame intCodeGame = new IntCodeGame();

        // System.out.print(intCodeGame.getBlocks());
    }
}
