package com.zn.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangnan
 * @version 1.0
 * @date 2020/12/15 15:50
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        // 停车位，6辆车争抢3个停车位
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                try {
                    semaphore.acquire(); // 获取许可
                    System.out.println(Thread.currentThread().getName() + "->停入车位");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + "->驶出车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(); // 释放许可
                }
            },String.valueOf(i)).start();
        }
    }

}
