package com.zn.lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author zhangnan
 * @version 1.0
 * @date 2020/12/15 10:58
 */
// java线程实现方式
public class ThreadTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Thread
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "->thread run");
        },"a").start();
        // Runnable
        new Thread(new MyThread2(),"b").start();
        // callable
        FutureTask<String> futureTask = new FutureTask<>(new MyThread1());
        new Thread(futureTask,"c").start();
        System.out.println("============" + futureTask.get());
    }
}

class MyThread1 implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "->call");
        return "done";
    }
}

class MyThread2 implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "->Runnable run");
    }
}