package com.placeholder;

import java.io.BufferedReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by L on 2017/8/21.
 */
public class LambdaDemo {
    public static void main(String[] args) {
        Random r = new Random();
        int[] arr = generatorInt(10000000);
        long start = System.nanoTime();
        px1(arr);
        long end = System.nanoTime();
        System.out.println(end - start);

//        Stream<String> stream = list.stream();
//        Stream<String> stringStream = stream.filter(s -> !s.equals("test"));
//        List<String> collect = stringStream.collect(Collectors.toList());
//        System.out.println(collect);
    }

    static int[] generatorInt(int n) {
        Random r = new Random();
        int[] arr = new int[n];
        for (int i=0; i<n; i++) {
            arr[i] = r.nextInt();
        }
        return arr;
    }

    static void px1(int[] arr) {
        Arrays.stream(arr).sorted().toArray();
    }

    static void px2(int[] arr) {
        Arrays.sort(arr);
    }

    static void sort1(String[] arr) {
        Arrays.sort(arr);
    }

    static void sort2(String[] arr) {
        Arrays.sort(arr, (o1, o2) -> o1.compareToIgnoreCase(o2));
    }

    static void sort3(String[] arr) {
        Arrays.sort(arr, String::compareToIgnoreCase);
    }

}
