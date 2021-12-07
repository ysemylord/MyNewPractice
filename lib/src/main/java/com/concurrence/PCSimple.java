package com.concurrence;

/**
 * 实现一个简单的生成这和消费者
 * 生产者上产一个，消费者消费一个
 */

class Res {
    private int count = 0;
    private boolean flag;

    public synchronized void put() {
        if (!flag) {
            flag = true;
            count++;
            System.out.println(Thread.currentThread().getName() + " put number" + count);
            notify();//唤醒等待得消费者
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void out() {
        if (flag) {
            flag = false;
            System.out.println(Thread.currentThread().getName() + " out number" + count);
            count--;
            notify();//唤醒等待得生产者
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class PCSimple {

    public static void main(String[] args) {
        Res res = new Res();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) res.put();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) res.out();
            }
        }).start();
    }

}
