package com.placeholder.io;

import java.io.*;
import java.util.Random;

/**
 * Created by L on 2017/8/20.
 */
public class _14ObjectSerialization implements Serializable {
    private static String filepath = "C:/Users/L/Desktop/data.txt";
    private static Random rand = new Random(47);
    private Data[] d = {
        new Data(rand.nextInt(10)),
        new Data(rand.nextInt(10)),
        new Data(rand.nextInt(10))
    };
    private _14ObjectSerialization next;
    private char c;

    public _14ObjectSerialization(int i, char x) {
        System.out.println("Worm constructor: " + i);
        c = x;
        if (--i > 0)
            next = new _14ObjectSerialization(i, (char)(x + 1));
    }
    public _14ObjectSerialization() {
        System.out.println("Default constructor");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(":");
        result.append(c).append("(");
        for (Data data : d)
            result.append(data);
        result.append(")");
        if (next != null)
            result.append(next);
        return result.toString();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        _14ObjectSerialization worm = new _14ObjectSerialization(6, 'a');
        System.out.println("w = " + worm);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
        oos.writeObject("Worm storage\n");
        oos.writeObject(worm);
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
        String s = (String)ois.readObject();
        _14ObjectSerialization w2 = (_14ObjectSerialization) ois.readObject();
        System.out.println(s + "w2 = " + w2);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(baos);
        oos2.writeObject("Worm storage\n");
        oos2.writeObject(worm);
        oos2.flush();
        oos2.close();

        ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
        s = (String) ois2.readObject();
        _14ObjectSerialization w3 = (_14ObjectSerialization) ois2.readObject();
        System.out.println(s + "w3 = " + w3);
    }
}
class Data implements Serializable {
    private int n;

    public Data(int n) {
        this.n = n;
    }

    public String toString() {
        return Integer.toString(n);
    }
}
