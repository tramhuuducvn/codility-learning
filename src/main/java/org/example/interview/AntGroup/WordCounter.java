package org.example.interview.AntGroup;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

import org.example.util.Logger;

public class WordCounter {

    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final ConcurrentMap<String, Integer> wordCountMap = new ConcurrentHashMap<>();

    public static void solution() {

    }

    public static void main(String[] args) throws InterruptedException {
        File file = new File("hello.txt");
        Logger.log("Where is my file: " + file.getAbsolutePath());
        // // Replace these paths with your actual file paths
        // List<String> filePaths = Arrays.asList("/question_1/file_1.txt",
        // "/question_1/file_2.txt");

        // ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        // List<Future<?>> futures = new ArrayList<>();

        // for (String path : filePaths) {
        // futures.add(executor.submit(() -> processFile(path)));
        // }

        // // Wait for all tasks to complete
        // for (Future<?> future : futures) {
        // try {
        // future.get();
        // } catch (ExecutionException e) {
        // e.printStackTrace();
        // }
        // }

        // executor.shutdown();

        // // Output results
        // wordCountMap.forEach((word, count) -> System.out.println(word + " " +
        // count));
    }

    private static void processFile(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.flatMap(line -> Arrays.stream(line.trim().split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .map(String::toLowerCase)
                    .forEach(word -> wordCountMap.merge(word, 1, Integer::sum));
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
    }
}
