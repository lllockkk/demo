package com.placeholder.jmx.hello;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class HelloTest {
    public static void main(String[] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.placeholder.jmx:type=Hello");
        Hello hello = new Hello();
        mbs.registerMBean(hello, name);
        System.out.println("Waiting forever...");
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
