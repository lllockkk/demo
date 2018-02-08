package com.placeholder.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by L on 2017/8/21.
 */
public class Demo {
    public static void main(String[] args) {
        List<Demo> list = new ArrayList<>();
        while (true) {
            list.add(new Demo());
        }
    }
}