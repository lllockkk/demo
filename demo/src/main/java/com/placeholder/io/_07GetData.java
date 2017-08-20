package com.placeholder.io;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by L on 2017/8/19.
 */
public class _07GetData {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(BSIZE);

        int i = 0;
        while (i++ < bb.limit())
            if (bb.get() != 0)
                System.out.print("nonzero");
        System.out.println("i = " + i);
        bb.rewind();

        // stotre and read a char array
        bb.asCharBuffer().put("Howdy!");
        char c;
        while ((c=bb.getChar()) != 0)
            System.out.print(c + " ");
        System.out.println();

        // store and read a short
        bb.asShortBuffer().put((short) 471142);
        System.out.println(bb.getShort());
        bb.rewind();

        // store and read a int
        bb.asIntBuffer().put(99471142);
        System.out.println(bb.getInt());
        bb.rewind();

        // store and read a long
        bb.asLongBuffer().put(99471142);
        System.out.println(bb.getLong());
        bb.rewind();

        // store and read a float
        bb.asFloatBuffer().put(99471142);
        System.out.println(bb.getFloat());
        bb.rewind();

        // store and read a double
        bb.asDoubleBuffer().put(99471142);
        System.out.println(bb.getDouble());
        bb.rewind();

    }
}
