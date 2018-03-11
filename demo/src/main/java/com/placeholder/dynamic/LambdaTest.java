package com.placeholder.dynamic;

import java.util.List;

/**
 * Created by L on 2018/2/23.
 */
public class LambdaTest {
    public static void main(String[] args) {
        Runnable r = () -> {
            System.out.println("hello world!");
        };
        System.out.println(r);
    }
}
