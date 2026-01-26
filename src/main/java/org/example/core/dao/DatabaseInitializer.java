package org.example.core.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void createTables(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();

        statement.executeUpdate("""
            CREATE TABLE IF NOT EXISTS meals (
                meal_id INTEGER,
                category VARCHAR(15),
                meal VARCHAR(50)
            )
        """);

        statement.executeUpdate("""
            CREATE TABLE IF NOT EXISTS ingredients (
                ingredient_id SERIAL PRIMARY KEY,
                name VARCHAR(50),
                amount DOUBLE PRECISION,
                unit VARCHAR(10),
                meal_id INTEGER
            )
        """);

        statement.executeUpdate("""
            CREATE TABLE IF NOT EXISTS plan (
                day VARCHAR(15),
                meal_option VARCHAR(50),
                meal_category VARCHAR(15),
                meal_id INTEGER
            )
        """);

        statement.close();
    }
}
