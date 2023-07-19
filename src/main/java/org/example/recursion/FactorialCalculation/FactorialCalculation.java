package org.example.recursion.FactorialCalculation;

public class FactorialCalculation {
    public static long solution(long n) {
        if (n < 0) {
            System.out.println("ERROR: Negative Number");
            return -1;
        }

        long ret;
        if (n == 0) {
            return 1;
        } else {
            ret = n * solution(n - 1);
        }
        return ret;
    }
}
