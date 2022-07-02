import java.util.Scanner;

//Task 1
public class CalculatingOptionsBrackets {

    public static void calculatingOptinsBrackets() {
        System.out.println("Enter number brackets bigger 0 and integer ");
        int number = new Scanner(System.in).nextInt();

        System.out.println(getNumberOfCorrectOptions(number));
    }


    // S(n) = S(i - 1)*S(n - i) and S(0) = 1;
    public static int getNumberOfCorrectOptions(int number) {

        int sum = 0;
        // Return the one correct options, if the entered number is 0
        if (number == 0) {
            return sum = 1;
        }
        // Calculating a number of correct options
        for (int i = 1; i <= number; i++) {
            sum += getNumberOfCorrectOptions(i - 1) * getNumberOfCorrectOptions(number - i);
        }
        return sum;
    }

}
