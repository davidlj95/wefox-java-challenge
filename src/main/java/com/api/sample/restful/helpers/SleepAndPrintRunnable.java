package com.api.sample.restful.helpers;

public class SleepAndPrintRunnable implements Runnable {

    private final Integer i;

    public SleepAndPrintRunnable(Integer seconds) {
        this.i = seconds;
    }

    @Override
    public void run() {
        System.out.println(
                String.format("Thread %d: started", i)
        );
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException e) {
            System.out.println("Thread %d: fail to sleep");
            e.printStackTrace();
        }
        System.out.println(
                String.format("Thread %d: finished", i)
        );
    }

}
