package com.placeholder;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by L on 2018/2/20.
 */
public class Reference {
    private final static int MB = 1 << 20;
    /*
    * -Xmx20M -Xms20M -XX:+PrintGCDetails
    * */
    public static void main(String[] args) {
        String a = new StringBuilder("com.placeholder.").append("Demo").toString();
        System.out.println(a == a.intern());
//        weak();
    }

    public static void soft () {
        SoftReference sr = new SoftReference<>(new byte[10 * MB]);
//        byte[] b1 = new byte[10 * MB];
//        SoftReference soft = new SoftReference(b1);

        System.out.println(sr.get());
        System.gc();
        System.out.println(sr.get());
        byte[] b2 = new byte[10 * MB];
        System.out.println(sr.get());
    }

    public static void weak() {
        WeakReference wr = new WeakReference<>(new byte[10 * MB]);
//        byte[] b1 = new byte[10 * MB];
//        WeakReference wr = new WeakReference<>(b1);

        System.out.println(wr.get());
        System.gc();
        System.out.println(wr.get());
    }

    public static void phantom() {
        // TODO: 2018/2/19
    }
}
