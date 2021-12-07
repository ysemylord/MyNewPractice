package com.concurrence.b_stop;



public class StopTestThree {
    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                System.out.println(Thread.currentThread().getName()+" is running");
            }
        }
    }
    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
