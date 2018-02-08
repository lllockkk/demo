package com.placeholder;


import sun.misc.Launcher;

import java.io.*;
import java.nio.channels.FileChannel;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.prefs.Preferences;

public class Demo {
    private static String filepath = "C:/Users/L/Desktop/data.txt";
    private static String path = "C:/Users/L/Desktop/";
    private static int length = 0x8000000;
    private static FileChannel fc;

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        ClassLoader classLoader = Demo.class.getClassLoader();

//        Preferences system = Preferences.systemRoot();
//        Preferences user = Preferences.userRoot();
//        System.out.println(user);
    }
}






















