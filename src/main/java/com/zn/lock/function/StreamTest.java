package com.zn.lock.function;

/**
 * @author zhangnan
 * @version 1.0
 * @date 2020/12/17 15:18
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * stream测试，题目如下
 * 有5个用户，筛选：
 * 1：ID必须是偶数
 * 2：年龄必须大于23岁
 * 3：用户名转为大写字母
 * 4：用户名字母倒着排序
 * 5：只输出一个用户
 */
public class StreamTest {
    public static void main(String[] args) {
        User user1 = new User(1, "a", 21);
        User user2 = new User(2, "a", 22);
        User user3 = new User(3, "a", 23);
        User user4 = new User(4, "a", 24);
        User user5 = new User(5, "a", 25);
        // 使用stream流计算

    }
}

@Data
@AllArgsConstructor
class User{
    private int id;
    private String name;
    private int age;
}