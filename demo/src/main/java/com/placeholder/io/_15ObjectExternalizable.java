package com.placeholder.io;

import java.io.*;

/**
 * Created by L on 2017/8/20.
 */
public class _15ObjectExternalizable {
    private static String filepath = "C:/Users/L/Desktop/data.txt";
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Constructing objects");
        Blip1 b1 = new Blip1();
        Blip2 b2 = new Blip2("A String ", 47);
        System.out.println(b2);
        Blip3 b3 = new Blip3();

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
        System.out.println("Saving objects:");
        oos.writeObject(b1);
        oos.writeObject(b2);
        oos.writeObject(b3);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
        System.out.println("Recovering b1:");
        b1 = (Blip1) ois.readObject();

        System.out.println("Recovering b2:");
        b2 = (Blip2) ois.readObject();
        System.out.println(b2);

        // 因为 Blip2 默认构造方法不是public，所以会抛异常
        System.out.println("Recovering b3");
        b3 = (Blip3)ois.readObject();
    }
}

class Blip1 implements Externalizable {
    public Blip1() {
        System.out.println("Blip1 Constructor");
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip1.writeExternal");
    }
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip1.readExternal");
    }
}

class Blip2 implements Externalizable {
    private int i;
    private String s;

    public Blip2() {
        System.out.println("Blip2 Constructor");
    }

    public Blip2(String x, int a) {
        s = x;
        i = a;
    }

    public String toString() {
        return s + i;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip2.writeExternal");
        out.writeObject(s);
        out.writeInt(i);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip2.readExternal");
        s = (String) in.readObject();
        i = in.readInt();
    }
}

class Blip3 implements Externalizable {
    Blip3() {
        System.out.println("Blip3 Constructor");
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip3.writeExternal");
    }
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip3.readExternal");
    }
}