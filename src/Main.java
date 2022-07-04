import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        //TEST 1
        CalculatingOptionsBrackets.calculatingOptinsBrackets();
        System.out.println();
        //TEST 2

        ParseFile.getCostPathDestination(ParseFile.parseFile());
        System.out.println();
        //TEST 3print sum all digits of the number 100!
        System.out.println("Sum digits 100! = " + SumDIgitsFactorial.sumDigits(SumDIgitsFactorial.getFactorial(100)));

    }
}
















