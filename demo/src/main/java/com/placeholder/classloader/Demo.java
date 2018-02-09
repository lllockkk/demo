package com.placeholder.classloader;

import com.sun.nio.zipfs.ZipFileStore;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Demo {
    public static final String file = "file:///D:/mvn-repo/commons-beanutils/commons-beanutils/1.9.3/commons-beanutils-1.9.3.jar";
    public static final String rt = "file:///D:/dev/Java/jdk1.8.0_152/jre/lib/rt.jar";
    public static final String extPath = "file:///D:/dev/Java/jdk1.8.0_152/jre/lib/ext/";
    public static final String classpath = "file:///D:/git-repo/demo/demo/target/classes/";

    public static int i = 1;

    public static void main(String[] args) throws Exception {

    }

    public void test() throws Exception {
        URLClassLoader cl1 = getClassLoader(null);
        Class<?> c1 = cl1.loadClass("com.placeholder.classloader.Demo");
        System.out.println(c1 == Demo.class);
        // ClassCaseException 不同的Class实例创建的对象
        Demo o = (Demo) c1.newInstance();
    }

    public static URLClassLoader getClassLoader(ClassLoader parent) throws MalformedURLException {
        URLClassLoader urlClassLoader = new URLClassLoader(
                new URL[]{new URL(file), new URL(rt), new URL(classpath), new URL(extPath + "zipfs.jar")}, parent);
        return urlClassLoader;
    }
}



