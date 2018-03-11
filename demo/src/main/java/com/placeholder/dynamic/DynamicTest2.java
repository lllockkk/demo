package com.placeholder.dynamic;

import java.lang.invoke.*;
import java.lang.reflect.Method;

/**
 * Created by L on 2018/2/23.
 */
public class DynamicTest2 {
    public static void main(String[] args) throws Throwable {
        MethodType mt = MethodType.methodType(void.class, String.class);
        MethodHandle mh = MethodHandles.lookup().findStatic(DynamicTest2.class, "testMethod", mt);
        CallSite cs = new ConstantCallSite(mh);
        cs.dynamicInvoker().invoke("hello world!");
//        mh.invokeExact("hello world!");
    }
    public static void testMethod(String s) {
        System.out.println("testMethod:" + s);
    }
}
