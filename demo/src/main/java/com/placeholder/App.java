package com.placeholder;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ReflectiveOperationException {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 300000; i++)
        {
            Test test = new Test();
            if (!list.contains(test.hashCode()))
                list.add(test.hashCode());
        }
        System.out.println(list.size());
    }

}

class Test {

}

class A {
    int i;
    A(int i) {
        this.i = i;
        System.out.println("A: " + i);
    }
}
