package com.placeholder;


import java.io.*;
import java.nio.channels.FileChannel;
import java.util.prefs.Preferences;

public class Demo {
    private static String filepath = "C:/Users/L/Desktop/data.txt";
    private static String path = "C:/Users/L/Desktop/";
    private static int length = 0x8000000;
    private static FileChannel fc;

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Preferences system = Preferences.systemRoot();
        Preferences user = Preferences.userRoot();
        System.out.println(user);
    }
}

class Obj implements Serializable {
    public static int i = 1;
    public Obj() {
        i = 2;
    }
}





















