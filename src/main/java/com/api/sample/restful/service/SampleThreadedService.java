package com.api.sample.restful.service;

import com.api.sample.restful.helpers.SleepAndPrintRunnable;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class SampleThreadedService {

    public void runThreeThreadsAtOnceAndThenAFinalOne() {

        // Run three in parallel
        ExecutorService executor = Executors.newFixedThreadPool(3);
        for(int i = 1; i <= 3; i++) {
            executor.submit(new SleepAndPrintRunnable(i));
        }
        this.awaitTermination(executor);

        // Run a last one
        Thread thread = new Thread(new SleepAndPrintRunnable(4));
        thread.start();
        try {
            thread.join(5000);
        } catch (InterruptedException e) {
            thread.interrupt();
            e.printStackTrace();
        }

    }

    private void awaitTermination(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
