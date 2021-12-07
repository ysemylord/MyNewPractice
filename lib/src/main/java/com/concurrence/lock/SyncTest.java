package com.concurrence.lock;

/**
 * 可重入锁
 */
public class SyncTest {

    public static void main(String[] args) {
        SyncTest syncTest = new SyncTest();
        syncTest.test1();
        System.out.println(syncTest.count);
    }

    int count = 0;

    /**
     * synchronized为可重入锁
     */
    public synchronized void test1() {
        count++;
        test2();
    }

    public synchronized void test2() {
        count--;
    }

}
