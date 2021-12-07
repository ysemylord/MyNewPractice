package com.concurrence.b_stop;


public class StopTestTwo {
    static class MyThread extends Thread {
        @Override
        public void run() {
            while(!isInterrupted()){
                System.out.println(getName()+" is running");
            }
        }
    }
    public static void main(String[] args) {
        MyThread thread = new MyThread();
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
