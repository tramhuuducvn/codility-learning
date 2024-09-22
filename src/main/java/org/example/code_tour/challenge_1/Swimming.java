package org.example.code_tour.challenge_1;

import java.util.Scanner;

public class Swimming {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int k = scanner.nextInt();
        scanner.close();

        int t = (int) Math.ceil((double) n / k);
        int result = (int) (t * (t + 1) / 2);
        System.out.println(result);
    }
}
