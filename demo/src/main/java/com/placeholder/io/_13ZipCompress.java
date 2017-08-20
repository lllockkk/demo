package com.placeholder.io;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * Created by L on 2017/8/20.
 */
public class _13ZipCompress {
    private static String path = "C:/Users/L/Desktop/";
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream(path + "data.zip");
        CheckedOutputStream csum = new CheckedOutputStream(fos, new Adler32());
        ZipOutputStream zos = new ZipOutputStream(csum);
        BufferedOutputStream out = new BufferedOutputStream(zos);
        zos.setComment("A test of Java Zipping");

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(path + "data.txt"));
        zos.putNextEntry(new ZipEntry("data.txt"));
        int c;
        while ((c = in.read()) != -1) {
            out.write(c);
        }
        in.close();
        out.flush();
        out.close();
        System.out.println("Checksum: " + csum.getChecksum().getValue());

        System.out.println("Reading file");
        FileInputStream fis = new FileInputStream(path + "data.zip");
        CheckedInputStream cis = new CheckedInputStream(fis, new Adler32());
        ZipInputStream zis = new ZipInputStream(cis);
        BufferedInputStream bis = new BufferedInputStream(zis);
        ZipEntry ze;
        while ((ze = zis.getNextEntry()) != null) {
            int x;
            while ((x = bis.read()) != -1) {
                System.out.print((char)x);
            }
        }
        System.out.println("Checksum: " + cis.getChecksum().getValue());
        bis.close();

        ZipFile zf = new ZipFile(path + "data.zip");
        Enumeration<? extends ZipEntry> entries = zf.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            System.out.println("File: " + entry);

        }
    }
}




















