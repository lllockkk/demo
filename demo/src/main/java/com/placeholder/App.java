package com.placeholder;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        String regex = "http://angularjs.cn/api/article/latest\\?(p=\\d+&)?s=20";
        String text = "http://angularjs.cn/api/article/latest?s=20";
        boolean flag = text.matches(regex);
        System.out.println(flag);

    }

}

class Test {
    static A a = new A(1);
    static {
        a = new A(2);
    }
    public Test() {
        System.out.println(a.i);
    }

}

class A {
    int i;
    A(int i) {
        this.i = i;
        System.out.println("A: " + i);
    }
}
