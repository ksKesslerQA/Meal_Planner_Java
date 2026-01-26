package org.example.core.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCleaner {

    public static void clearAll(Connection connection) throws SQLException {
        Statement st = connection.createStatement();

        st.executeUpdate("DELETE FROM plan");
        st.executeUpdate("DELETE FROM ingredients");
        st.executeUpdate("DELETE FROM meals");

        st.close();
    }
}
