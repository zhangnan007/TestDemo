package com.zn.lock;

import java.util.concurrent.locks.Lock;

/**
 * @author zhangnan
 * @version 1.0
 * @date 2020/12/10 17:23
 */
public class Singleton {

    // volatile重排序语义可防止指令重排序
    private volatile Singleton singleton = null;

    private Singleton(){}

    public Singleton getInstance(){
        if(singleton == null){
            synchronized (this){
                if(singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

}