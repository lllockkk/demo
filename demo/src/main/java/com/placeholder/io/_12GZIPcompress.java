package com.placeholder.io;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by L on 2017/8/20.
 */
public class _12GZIPcompress {
    private static String path = "C:/Users/L/Desktop/";
    public static void main(String[] args) throws IOException, InterruptedException {
        // 写 data.txt
        BufferedWriter out = new BufferedWriter(new FileWriter(path + "data.txt"));
        out.write("hello中文world");
        out.flush();
        out.close();

        // data.txt 压缩成 data.gz
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path + "data.txt"));
        BufferedOutputStream out2 = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(path + "data.gz")));
        System.out.println("Writing file");
        int c;
        while ((c = in.read()) != -1) {
            out2.write(c);
        }
        in.close();
        out2.close();

        // 读取data.gz
        System.out.println("Reading file");
        BufferedReader in2 = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(path + "data.gz"))));
        String s;
        while ((s = in2.readLine()) != null) {
            System.out.println(s);
        }
    }
}
