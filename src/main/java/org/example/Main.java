package org.example;

import java.util.Random;
import java.util.ArrayList;

public class Main {
    static class OperationStats {
        long time;
        int operations;

        OperationStats(long time, int operations) {
            this.time = time;
            this.operations = operations;
        }
    }

    public static void main(String[] args) {
        BinomialHeap heap = new BinomialHeap();
        Random random = new Random();
        int[] numbers = new int[10000];
        ArrayList<OperationStats> insertStats = new ArrayList<>();
        ArrayList<OperationStats> searchStats = new ArrayList<>();
        ArrayList<OperationStats> deleteStats = new ArrayList<>();

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100000);
        }

        for (int num : numbers) {
            long startTime = System.nanoTime();
            int[] ops = new int[2];
            heap.insert(num, ops);
            long endTime = System.nanoTime();
            insertStats.add(new OperationStats(endTime - startTime, ops[0] + ops[1]));
        }

        for (int i = 0; i < 100; i++) {
            int key = numbers[random.nextInt(numbers.length)];
            long startTime = System.nanoTime();
            int[] ops = new int[2];
            heap.search(key, ops);
            long endTime = System.nanoTime();
            searchStats.add(new OperationStats(endTime - startTime, ops[0] + ops[1]));
        }

        for (int i = 0; i < 1000; i++) {
            int key = numbers[random.nextInt(numbers.length)];
            long startTime = System.nanoTime();
            int[] ops = new int[2];
            heap.delete(key, ops);
            long endTime = System.nanoTime();
            deleteStats.add(new OperationStats(endTime - startTime, ops[0] + ops[1]));
        }

        double avgInsertTime = insertStats.stream().mapToLong(s -> s.time).average().orElse(0);
        double avgInsertOperations = insertStats.stream().mapToInt(s -> s.operations).average().orElse(0);

        double avgSearchTime = searchStats.stream().mapToLong(s -> s.time).average().orElse(0);
        double avgSearchOperations = searchStats.stream().mapToInt(s -> s.operations).average().orElse(0);

        double avgDeleteTime = deleteStats.stream().mapToLong(s -> s.time).average().orElse(0);
        double avgDeleteOperations = deleteStats.stream().mapToInt(s -> s.operations).average().orElse(0);

        System.out.printf("Insert - Avg time: %.2f ns, Avg operations: %.2f\n",
                avgInsertTime, avgInsertOperations);
        System.out.printf("Search - Avg time: %.2f ns, Avg operations: %.2f\n",
                avgSearchTime, avgSearchOperations);
        System.out.printf("Delete - Avg time: %.2f ns, Avg operations: %.2f\n",
                avgDeleteTime, avgDeleteOperations);
        
    }
}