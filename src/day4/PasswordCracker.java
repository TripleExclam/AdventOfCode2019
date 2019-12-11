package day4;

public class PasswordCracker {
    private static final int rangeStart = 125730;
    private static final int rangeEnd = 579381;

    public static boolean verifyCode(String pass) {
        int[] numbers = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        char previous = 'A';

        for (int i = 0; i < pass.length() - 1; i++) {
            if (pass.charAt(i) > pass.charAt(i + 1)) {
                return false;
            }
        }

        for (int i = pass.length() - 1; i >= 0; i--) {
            if (previous == pass.charAt(i)) {
                numbers[Character.getNumericValue(pass.charAt(i))]++;
            }
            previous = pass.charAt(i);
        }
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 1) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int solutionCount = 0;
        String pass;

        for (int i = rangeStart; i <= rangeEnd; i++) {
            if (verifyCode(String.valueOf(i))) {
                System.out.println(i);
                solutionCount++;
            }
        }
        System.out.println("There are: " + solutionCount);
    }


}
