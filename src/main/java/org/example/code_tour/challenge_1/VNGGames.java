package org.example.code_tour.challenge_1;

import java.util.Scanner;

public class VNGGames {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int K = scanner.nextInt();
        long N = 1;
        for (int i = 0; i < K; ++i) {
            long temp = scanner.nextLong();
            N *= temp;
        }

        long C = scanner.nextLong();

        int count = 0;

        while (true) {
            double temp = Math.cbrt((double) count * (double) N + (double) C);
            if (temp == (int) temp) {
                System.out.println((int) temp);
                scanner.close();
                return;
            }
        }

    }
}
