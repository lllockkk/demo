package com.placeholder.rbtree;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Test {
    public static void main(String[] args) throws Exception {

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            Map<Integer, Integer> tree = new RedBlackTree<>();
//            Map<Integer, Integer> tree = new TreeMap<>();
            test(tree);
//            tree.check();
            System.out.println(tree.size());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

    public static void test(Map<Integer, Integer> tree) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Test.class.getClassLoader().getResourceAsStream("nums10000.txt")));
        List<String> list = new ArrayList<>();
        String ln;
        while ((ln = reader.readLine()) != null) {
            list.add(ln);
        }
        for (int i = 0; i < list.size(); i++) {
            tree.put(i, null);
        }
        for (int i = 0; i < list.size(); i++) {
            if (i % 3 == 0) {
                tree.remove(i);
            }
        }
    }
}


