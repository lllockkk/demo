package com.placeholder;

import javax.sound.midi.Soundbank;

/**
 * Created by l on 17-7-14.
 */

/**
 * 使用finalize验证书是否归还
 */
public class TerminationCondition {
    public static void main(String[] args) {
        Book novel = new Book(true);
        novel.checkIn();
        new Book(true);
        System.gc();
        System.out.println("end");
    }
}

class Book {
    boolean checkedOut = false;
    Book(boolean checkOut) {
        checkedOut = checkOut;
    }
    void checkIn() {
        checkedOut = false;
    }
    @Override
    protected void finalize() throws Throwable {
        if(checkedOut)
            System.out.println("Error: checked out");
    }
}
