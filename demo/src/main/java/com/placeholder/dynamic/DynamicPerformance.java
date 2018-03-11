package com.placeholder.dynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by L on 2018/2/23.
 */
public class DynamicPerformance {
    public static void main(String[] args) throws Throwable {
        test();
    }

    public static void testMethod() {
        System.out.println("hello world");
    }

    public static void test() throws Throwable {
        long begin = System.currentTimeMillis();
        for (int i=0; i<100; i++) {
            reflect();
        }
        System.out.println(System.currentTimeMillis() - begin);
    }

    public static void invoke() throws Throwable {
        MethodHandle mh = MethodHandles.lookup().findStatic(DynamicPerformance.class, "testMethod", MethodType.methodType(void.class));
        mh.invokeExact();
    }
    public static void reflect() throws Throwable {
        DynamicPerformance.class.getMethod("testMethod").invoke(null);
    }
}
