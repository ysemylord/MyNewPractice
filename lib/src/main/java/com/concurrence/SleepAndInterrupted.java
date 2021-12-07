package com.concurrence;

public class SleepAndInterrupted {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("1");
                }
            }
        });
        thread.start();
        thread.interrupt();
    }
}
