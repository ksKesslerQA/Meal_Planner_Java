package org.example;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void createTables(Connection connection) throws SQLException {

        Statement statement = connection.createStatement();

        statement.executeUpdate("""
            CREATE TABLE IF NOT EXISTS meals (
                meal_id INTEGER,
                category VARCHAR(50),
                meal VARCHAR(50)
            )
        """);

        statement.executeUpdate("""
            CREATE TABLE IF NOT EXISTS ingredients (
                ingredient_id INTEGER,
                ingredient VARCHAR(50),
                meal_id INTEGER
            )
        """);

        statement.close();
    }
}
