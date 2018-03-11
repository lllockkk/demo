package com.placeholder;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.io.*;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Selector;
import java.util.concurrent.Callable;

/**
 * Created by L on 2018/2/21.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        StringReader sr = new StringReader("hello");
        PushbackReader pr = new PushbackReader(sr);
//        int i;
//        while ((i=pr.read()) != -1) {
//            System.out.println(i);
//        }
        System.out.println(pr.read());
        pr.unread(12);
        System.out.println(pr.read());
        System.out.println(pr.read());
        System.out.println(pr.read());
        System.out.println(pr.read());
    }
}
