package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImageExtractor {

    public static final int width = 25;
    public static final int height = 6;

    private int[][] image;


    public ImageExtractor() throws IOException {
        image = new int[25][6];
        BufferedReader reader = new BufferedReader(
                new FileReader("src/day8/imageData.txt"));
        String line = reader.readLine();
        int minZeros = Integer.MAX_VALUE;
        int minFrame = 0;
        int zeroCount;
        int frame = 0;
        while (frame < line.length()) {
            zeroCount = 0;
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    char pixel = line.charAt(frame + j * width + i);
                    if (pixel == '0') {
                        if (image[i][j] != -3) {
                            image[i][j] = -2;
                        }
                        zeroCount++;
                    } else if (pixel == '1') {
                        if (image[i][j] != -2) {
                            image[i][j] = -3;
                        }
                    }
                }
            }
            if (zeroCount < minZeros) {
                minFrame = frame;
                minZeros = zeroCount;
            }
            frame += width * height;
        }
        int onesCount = 0;
        int twosCount = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                char pixel = line.charAt(minFrame + j * width + i);
                if (pixel == '1') {
                    onesCount++;
                } else if (pixel == '2') {
                    twosCount++;
                }
            }
        }
        System.out.println(onesCount *  twosCount);
        dumpImage();
    }

    public void dumpImage() {

        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (image[i][j] == -3) {
                    System.out.print("#");
                } else if (image[i][j] == -2) {
                    System.out.print(" ");
                } else {
                    System.out.print("0");
                }

            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws IOException {
        ImageExtractor im = new ImageExtractor();
    }

}
