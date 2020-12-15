package com.zn.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangnan
 * @version 1.0
 * @date 2020/12/15 13:52
 */
// 读写锁示例
public class ReadWriteLockTest {

    public static void main(String[] args) {
        MyCacheLock myCacheLock = new MyCacheLock();
        // 写入
        for (int i = 0; i < 10; i++) {
            final String tmp = String.valueOf(i);
            new Thread(()->{
                myCacheLock.put(tmp,tmp);
            },String.valueOf(i)).start();
        }
        // 读取
        for (int i = 0; i < 10; i++) {
            final String tmp = String.valueOf(i);
            new Thread(()->{
                myCacheLock.get(tmp);
            },String.valueOf(i)).start();
        }

    }

}
class MyCacheLock{
    private volatile Map<String,Object> myCache = new HashMap<>();
    // 更加细粒度的控制
    ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 写入
    public void put(String key,Object obj){
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "->写入开始：" + key);
            myCache.put(key,obj);
            System.out.println(Thread.currentThread().getName() + "->写入OK：" + key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    // 读取
    public void get(String key){
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "->获取开始：" + key);
            String value = (String)myCache.get(key);
            System.out.println(Thread.currentThread().getName() + "->获取ok：" + value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }


}