package com.placeholder;

import org.apache.camel.component.bean.AmbiguousMethodCallException;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
//        System.out.println(((byte)0xff) & 0xff);

//        BitMap bitMap = new BitMap();
//        bitMap.set(0);
//        bitMap.set(1);
//        bitMap.set(1);
//        bitMap.set(7);
//        bitMap.set(8);
//
//        for (int i = 0; i < 9; i++) {
//            System.out.println(i + " is set? " + bitMap.isSet(i));
//        }

        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int value = r.nextInt();
            System.out.println(Integer.numberOfTrailingZeros(value) + ":" + numberOfTrailingZeros(value));
        }
        System.out.println(Integer.numberOfTrailingZeros(0) + ":" + numberOfTrailingZeros(0));
        System.out.println(Integer.numberOfTrailingZeros(1) + ":" + numberOfTrailingZeros(1));
        System.out.println(Integer.numberOfTrailingZeros(0xffffffff) + ":" + numberOfTrailingZeros(0xffffffff));
    }

    public static int numberOfTrailingZeros(int i) {
        if (i == 0) {
            return 32;
        }
        int j;
        int n = 31;
        j = i<<16; if (j != 0) { i=j;n -= 16; }
        j = i<<8; if (j != 0) { i=j;n -= 8; }
        j = i<<4; if (j != 0) { i=j;n -= 4; }
        j = i<<2; if (j != 0) { i=j;n -= 2; }
        j = i<<1; if (j != 0) { i=j;n -= 1; }
        return n;
    }
}

//
//class BitMap {
//    private byte[] arr;
//
//    // 一个byte的位数
//    private final static int SIZE = Byte.SIZE;
//    private final static int ADDRESS_BITS_PER_WORD = 3;
//    private final static int BITS_PER_WORD = 1 << ADDRESS_BITS_PER_WORD;
//    private final static int BIT_INDEX_MASK = BITS_PER_WORD - 1;
//
//    private static final long WORD_MASK = 0xffffffffffffffffL;
//
//    public BitMap() {
//        arr = new byte[8];
//    }
//
//    public void set(int bit) {
//        int index = bit >> ADDRESS_BITS_PER_WORD;
//        byte bitIndex = (byte) (bit & BIT_INDEX_MASK);
//        arr[index] |= (1<<bitIndex);
//    }
//
//    public boolean isSet(int bit) {
//        int index = bit >> ADDRESS_BITS_PER_WORD;
//        byte bitIndex = (byte) (bit & BIT_INDEX_MASK);
//        int i = 1 << bitIndex;
//        return (arr[index] & i) == i;
//    }
//
//    public int nextSetBit(int bit) {
//        int index = bit >> ADDRESS_BITS_PER_WORD;
//        byte bitIndex = (byte) (bit & BIT_INDEX_MASK);
//        arr[index]
//    }
//
//}
//







