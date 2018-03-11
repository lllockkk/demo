package com.placeholder.dynamic;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Created by L on 2018/2/22.
 */
public class DynamicTest {
    public static void main(String[] args) throws Throwable {
//        Object obj = new ClassA();
        Object obj = System.out;
        getPrintlnMH(obj).invokeExact("hello");
    }

    private static MethodHandle getPrintlnMH(Object obj) throws Exception {
        MethodType mt = MethodType.methodType(void.class, String.class);
        return MethodHandles.lookup().findVirtual(obj.getClass(), "println", mt).bindTo(obj);
    }

    static class ClassA {
        public void println(String s) {
            System.out.println("class A:" + s);
        }
    }
}
