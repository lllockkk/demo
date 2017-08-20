package com.placeholder.io;

import java.io.*;
import java.util.Date;

/**
 * Created by L on 2017/8/20.
 */
public class _16Transient {
    private static String filepath = "C:/Users/L/Desktop/data.txt";
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Logon l = new Logon("Hulk", "pwd123");
        System.out.print("logon a = " + l);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath));
        oos.writeObject(l);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath));
        l = (Logon) ois.readObject();
        ois.close();
        System.out.print("logon a = " + l);
    }
}

class Logon implements Serializable {
    private Date date = new Date();
    private String username;
    private transient String password;

    public Logon(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toString() {
        return "logon info: \n username: " + username +
                "\n date: " + date + "\n password: " + password + "\n";
    }
}
