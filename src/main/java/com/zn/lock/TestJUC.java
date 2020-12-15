package com.zn.lock;

import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangnan
 * @version 1.0
 * @date 2020/12/11 15:06
 */
public class TestJUC {

    public static void main(String[] args) {
        Num1 num1 = new Num1();
        new Thread(() -> {
            try {
                num1.printdOddOrder();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "1").start();
        new Thread(() -> {
            try {
                num1.printEvenOrder();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "2").start();
    }
}

class Num1 {
    // 公用lock对象
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    int[] odd = new int[]{1,3,5,7,9};
    int[] even = new int[]{2,4,6,8,10};
    // 顺序执行，先执行odd打印，再执行even打印 start
    public void printdOddOrder() throws Exception {
        lock.lock();
        try {
            for (int i = 0; i < odd.length; i++) {
                System.out.println(odd[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printEvenOrder() throws Exception {
        lock.lock();
        try {
            for (int i = 0; i < even.length; i++) {
                System.out.println(even[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    // 顺序执行，先执行odd打印，再执行even打印 end

    // 交替执行 unpark版 start
    public void printdoddPark() throws InterruptedException {
        lock.lock();
        try {
            for (int i = 0; i < odd.length; i++) {
                System.out.println(odd[i]);
                condition2.signal();
                condition1.await();
            }
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printevenPark() throws InterruptedException {
        lock.lock();
        try {
            for (int i = 0; i < even.length; i++) {
                System.out.println(even[i]);
                condition1.signal();
                condition2.await();
            }
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    // 交替执行 unpark版 end

    // 交替执行 synchronize版 start
    public void printdodd() throws InterruptedException {
        synchronized (this) {
            for (int i = 0; i < odd.length; i++) {
                System.out.println(odd[i]);
                this.notify();
                this.wait();
            }
            this.notify();
        }
    }

    public void printeven() throws InterruptedException {
        synchronized (this) {
            for (int i = 0; i < even.length; i++) {
                System.out.println(even[i]);
                this.notify();
                this.wait();
            }
            this.notify();
        }
    }
    // 交替执行 synchronize版 end

    // 交替加减
    private int num = 0;
    Condition condition = lock.newCondition();
    public void incremented() {
        lock.lock();
        try {
            while (num != 0) {
                // 等待
                condition.await();
            }
            num++;
            System.out.println(Thread.currentThread().getName() + ": " + num);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decremented() {
        lock.lock();
        try {
            while (num == 0) {
                // 等待
                condition.await();
            }
            num--;
            System.out.println(Thread.currentThread().getName() + ": " + num);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}