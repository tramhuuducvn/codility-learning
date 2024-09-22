package org.example.recursion.CubeRoot;

public class CubeRoot {
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