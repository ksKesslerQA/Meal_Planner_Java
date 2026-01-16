package org.example;

import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) {
        String DB_URL = "jdbc:postgresql:meals_db";
        String USER = "postgres";
        String PASS = "root";

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected successfully!");

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
