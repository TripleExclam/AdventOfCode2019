package day1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MassCalculator {

    public static void main(String[] args) throws IOException {
        int fuel = 0;
        String line;
        BufferedReader reader = new BufferedReader(
                new FileReader("src/day1/ModuleMass.txt"));

        while ((line = reader.readLine()) != null) {
            int temp = Integer.parseInt(line);

            while (temp != 0) {
                temp = temp / 3 - 2;
                if (temp < 0) {
                    temp = 0;
                }
                fuel += temp;
            }
        }

        System.out.println(fuel);
    }

}
