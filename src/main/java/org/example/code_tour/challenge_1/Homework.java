package org.example.code_tour.challenge_1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Homework {

    public static int solution(List<Integer> tasks, List<Integer> TIME_A, List<Integer> TIME_B, int N,
            int D) {
        int totalTime = 0;

        Set<Integer> DONE_A = new HashSet<>();
        Set<Integer> DONE_B = new HashSet<>();
        int ca = 0;
        int cb = 0;

        tasks.size();

        for (int i = 0; i < N; ++i) {
            if (true) {

            }
            if (ca == D - 1) {
                // assign for B
                ca = 0;
                continue;
            }

            if (cb == D - 1) {
                // assign for A
                cb = 0;
                continue;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt();

        for (int i = 0; i < T; ++i) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            int d = scanner.nextInt();
            List<Integer> kindTasks = new ArrayList<>();

            for (int j = 0; j < n; ++j) {
                int key = scanner.nextInt() - 1;
                kindTasks.add(key);
            }

            List<Integer> timeA = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                timeA.add(scanner.nextInt());
            }

            List<Integer> timeB = new ArrayList<>();
            for (int j = 0; j < k; j++) {
                timeB.add(scanner.nextInt());
            }

            System.out.println(solution(kindTasks, timeA, timeB, n, d));
        }

        scanner.close();
    }
}
