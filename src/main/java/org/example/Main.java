package org.example;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        int[] data = rand.ints(10000, 1, 100000).toArray();

        BinomialHeap heap = new BinomialHeap();
        long totalInsertTime = 0;
        long totalInsertOps = 0;

        for (int value : data) {
            heap.resetOperationCount();
            long start = System.nanoTime();
            heap.insert(value);
            long end = System.nanoTime();
            totalInsertTime += (end - start);
            totalInsertOps += heap.getOperationCount();
        }

        int[] searchIndices = rand.ints(100, 0, 10000).toArray();
        long totalSearchTime = 0;
        long totalSearchOps = 0;

        for (int i : searchIndices) {
            heap.resetOperationCount();
            long start = System.nanoTime();
            heap.getMinimum();
            long end = System.nanoTime();
            totalSearchTime += (end - start);
            totalSearchOps += heap.getOperationCount();
        }

        int[] deleteIndices = rand.ints(1000, 0, 10000).toArray();
        long totalDeleteTime = 0;
        long totalDeleteOps = 0;

        for (int i = 0; i < 1000; i++) {
            heap.resetOperationCount();
            long start = System.nanoTime();
            heap.extractMinimum();
            long end = System.nanoTime();
            totalDeleteTime += (end - start);
            totalDeleteOps += heap.getOperationCount();
        }

        System.out.println("avg time insert: " + (totalInsertTime / 10000.0) + " ns, operations: " + (totalInsertOps / 10000.0));
        System.out.println("avg time search: " + (totalSearchTime / 100.0) + " ns, operations: " + (totalSearchOps / 100.0));
        System.out.println("avg time exctract: " + (totalDeleteTime / 1000.0) + " ns, operations: " + (totalDeleteOps / 1000.0));
    }
}