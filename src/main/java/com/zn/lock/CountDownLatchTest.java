package com.zn.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author zhangnan
 * @version 1.0
 * @date 2020/12/15 14:31
 */
// 计数器
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "->减一");
                countDownLatch.countDown(); // 减1
            },String.valueOf(i)).start();
        }
        countDownLatch.await(); // 计数器归零，然后才能向下执行
        System.out.println("计数完毕");
    }

}
