package com.placeholder.jdbc;

import com.sun.javaws.exceptions.ExitException;

import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/test";
        try (Connection conn = DriverManager.getConnection(url, "root", "123456")) {
            long begin = System.currentTimeMillis();
            transactionBatchInsert(conn, 100000); //1000000
            long end = System.currentTimeMillis();
            System.out.println(end - begin + "milliseconds");
        }
    }


    public static void insertByFor(Connection conn, int num) throws SQLException {
        Statement statement = conn.createStatement();
        for (int i=0; i<num; i++) {
            String insertSql = "INSERT INTO person(name) values('" + i + "')";
            statement.execute(insertSql);
        }
    }

    public static int[] batchInsert(Connection conn, int num) throws SQLException {
        Statement statement = conn.createStatement();
        for (int i=0; i<num; i++) {
            String insertSql = "INSERT INTO person(name) values('" + i + "')";
            statement.addBatch(insertSql);
        }
        int[] ints = statement.executeBatch();
        return ints;
    }

    public static void transactionInsert(Connection conn, int num) throws SQLException {
        conn.setAutoCommit(false);
        Statement statement = conn.createStatement();
        for (int i=0; i<num; i++) {
            String insertSql = "INSERT INTO person(name) values('" + i + "')";
            statement.execute(insertSql);
        }
        conn.commit();
    }

    public static void transactionBatchInsert(Connection conn, int num) throws SQLException {
        conn.setAutoCommit(false);
        Statement statement = conn.createStatement();
        for (int i=0; i<num; i++) {
            String insertSql = "INSERT INTO person(name) values('" + i + "')";
            statement.addBatch(insertSql);
        }
        statement.executeBatch();
        conn.commit();
    }

    // 10k 90ms
    // 缺点  Packet for query is too large (130000028 > 16777216)
    public static void sqlBatchInsert(Connection conn, int num) throws SQLException {
        Statement statement = conn.createStatement();
        StringBuilder sb = new StringBuilder("INSERT INTO person(name) VALUES('" + 0 + "')");
        for (int i=1; i<num; i++) {
            sb.append(",('").append(i).append("')");
        }
        statement.execute(sb.toString());
    }

    public static void preparedBatchInsert(Connection conn, int num) throws SQLException {
        conn.setAutoCommit(false);
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO person(name) VALUES(?)");
        for (int i=0; i<num; i++) {
            pstmt.setString(1, i + "");
            pstmt.addBatch();
        }
        pstmt.executeBatch();
        conn.commit();
    }
}
