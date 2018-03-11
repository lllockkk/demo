package com.placeholder.jvm;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Created by L on 2017/8/21.
 */
public class Demo {
    public static void main(String[] args) throws SQLException {
        //jdbc:<em>subprotocol</em>:<em>subname</em></code>
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test");
        System.out.println(connection);
    }
}

class A extends URLClassLoader {

    public A(URL[] urls) {
        super(urls);

    }



}