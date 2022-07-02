import java.math.BigInteger;

//Task3
public class SumDIgitsFactorial {

    //method return factorial of the number
    public static BigInteger getFactorial(int f) {
        BigInteger result = BigInteger.valueOf(1);
        for (int i = 1; i <= f; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    //method return sum all digits of the number
    public static int sumDigits(BigInteger number) {
        int sumDigits = 0;
        for (String a : number.toString().split("")) {
            sumDigits += Integer.parseInt(a);
        }
        return sumDigits;
    }
}
