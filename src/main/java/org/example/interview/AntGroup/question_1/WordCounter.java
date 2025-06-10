package org.example.interview.AntGroup.question_1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.example.util.Logger;

public class WordCounter {

    private static final String SRC_PATH = System.getProperty("user.dir")
            .concat("/src/main/java/org/example/interview/AntGroup/question_1/%s");

    // Replace NUM_THREADS = 1 for test different time.
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    private static final ConcurrentMap<String, Integer> countMap = new ConcurrentHashMap<>();

    private static void processFile(String filename) {
        try {
            // Assume that delay time for each files is 50 miliseconds.
            Thread.sleep(50);
        } catch (Exception e) {
            // TODO: handle exception
        }
        try (Stream<String> lines = Files.lines(Paths.get(String.format(SRC_PATH, filename)))) {
            lines.flatMap(line -> Arrays.stream(line.trim().split("\\s+")))
                    .filter(word -> !word.isEmpty())
                    .map(String::toLowerCase)
                    .forEach(word -> countMap.merge(word, 1, Integer::sum));
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
        }
    }

    private static void solution(List<String> filenames) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        Logger.log("NUM_THREADS: " + NUM_THREADS);
        List<Future<?>> futures = new ArrayList<>();

        for (String filename : filenames) {
            futures.add(executor.submit(() -> processFile(filename)));
        }

        // Wait for all tasks complete
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        // Print out the results
        countMap.forEach((word, count) -> System.out.println(word + " " + count));
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        solution(Arrays.asList("file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt",
                "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt",
                "file_1.txt", "file_2.txt", "file_3.txt", "file_1.txt", "file_2.txt", "file_3.txt"));
        long end = System.currentTimeMillis();
        System.out.println("Execute time: " + (end - startTime));
    }
}
