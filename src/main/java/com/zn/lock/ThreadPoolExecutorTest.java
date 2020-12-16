package com.zn.lock;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangnan
 * @version 1.0
 * @date 2020/12/16 17:53
 */
// 线程池例子，7个参数，4中拒绝策略
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {
        // 当核心线程和队列都被任务填满时，才会启动扩充最大线程数，线程池最大承载线程能力为：参数中的 队列数+最大线程数，该例子为 3+5=8个
        /**
         * 拒绝策略
         * new ThreadPoolExecutor.AbortPolicy() // 默认策略 超过最大承载能力后就不处理了，并抛出异常
         * new ThreadPoolExecutor.CallerRunsPolicy() // 超过最大承载能力后 哪里来的回哪里去，本例中多余的会交给main方法的线程去执行
         * new ThreadPoolExecutor.DiscardPolicy() // 超过最大承载能力后丢掉任务，不会抛出异常
         * new ThreadPoolExecutor.DiscardOldestPolicy() // 超过最大承载能力后尝试和最先执行的去竞争，不会抛出异常
         */
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        // 执行
        try {
            for (int i = 0; i < 2; i++) {
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " ->exe");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 执行完需要关闭线程池
            threadPool.shutdown();
        }
    }
}
