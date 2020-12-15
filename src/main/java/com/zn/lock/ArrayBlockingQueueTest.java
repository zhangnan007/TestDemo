package com.zn.lock;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangnan
 * @version 1.0
 * @date 2020/12/15 16:42
 */
// 阻塞队列
public class ArrayBlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        test4();
    }

    // 第一种  add/remove  element 会抛出异常
    public static void test1() {

        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // add 多添加会抛出异常 IllegalStateException: Queue full
//         System.out.println(blockingQueue.add("d"));
        // 检测队首元素 会抛出异常
        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // remove 多移除会抛出异常 java.util.NoSuchElementException
        // System.out.println(blockingQueue.remove());
    }

    // 第二种 offer poll peek 不会抛出异常
    public static void test2() {
        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
//        System.out.println(blockingQueue.offer("d")); 多存返回false

        // 检测队首元素  返回null
        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll()); // 多获取返回NULL
    }

    // 第三种 put take 一直阻塞等待
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);
        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
//        blockingQueue.put("d"); 多添加 会一直阻塞等待

        // 检测队首元素  无

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take()); // 多获取 会一直阻塞等待
    }

    // 第四种 offer poll 阻塞等待超时
    public static void test4() throws InterruptedException {
        ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3);
        blockingQueue.offer("a");
        blockingQueue.offer("b");
        blockingQueue.offer("c");
        blockingQueue.offer("d",2, TimeUnit.SECONDS); // 多添加会阻塞等待2秒,2秒后自动释放

        // 检测队首元素  无

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS)); // 多获取会阻塞等待2秒,2秒后自动释放
    }

}
