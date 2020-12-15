package com.zn.lock;

/**
 * 并发是交替执行
 * 并行是同时执行
 */
public class TestSynchronized {

    public static void main(String[] args) {
        Num num = new Num();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    num.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "incre").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    num.decerement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "decere").start();
    }
}

class Num {
    private int num = 0;

    public synchronized void increment() throws InterruptedException {
        if (num != 0) {
            // 等待
            this.wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName() + "->" + num);
        this.notify();
    }

    public synchronized void decerement() throws InterruptedException {
        if (num == 0) {
            // 等待
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "->" + num);
        this.notify();
    }
}