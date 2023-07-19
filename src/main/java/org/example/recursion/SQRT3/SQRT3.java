package org.example.recursion.SQRT3;

public class SQRT3 {
    public static double solution(double x) {
        double ret;

        if (x == 0) {
            ret = 0;
        } else if (x < 0) {
            ret = solution(-x);
        } else {
            ret = Math.exp(Math.log(x) / 3);
        }

        return ret;
    }
}