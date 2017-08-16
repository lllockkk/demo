package com.placeholder.io;

import java.io.*;

/**
 * Created by JSB-27 on 2017/8/15.
 */
public class _01BufferedInputFile {
    public static void main(String[] args) throws IOException {
        String s = read("E:\\git-repository\\demo\\demo\\src\\main\\java\\com\\placeholder\\io\\_01BufferedInputFile.java");
        System.out.println(s);
    }
    public static String read(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line=br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString();
    }
}
