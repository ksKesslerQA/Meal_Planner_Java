package org.example.dao;

import org.example.core.dao.MealDao;
import org.junit.jupiter.api.*;

import java.sql.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MealDaoTest {

    private Connection connection;
    private MealDao mealDao;

    @BeforeAll
    void setupDatabase() throws SQLException {

        String DB_URL = "jdbc:postgresql:meals_db_test";
        String USER = "postgres";
        String PASS = "root";

        connection = DriverManager.getConnection(DB_URL, USER, PASS);
        mealDao = new MealDao(connection);

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DROP TABLE IF EXISTS ingredients");
            stmt.executeUpdate("DROP TABLE IF EXISTS meals");

            stmt.executeUpdate("""
                CREATE TABLE meals (
                    meal_id INTEGER PRIMARY KEY,
                    category VARCHAR(15),
                    meal VARCHAR(50)
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE ingredients (
                    ingredient_id SERIAL PRIMARY KEY,
                    ingredient VARCHAR(50),
                    meal_id INTEGER
                )
            """);
        }
    }

    @AfterAll
    void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
/*
    @Test
    void testSaveAndRetrieveMeal() throws SQLException {
        Meal meal = new Meal("breakfast", "Omelette", List.of("eggs", "milk", "salt"));
        int mealId = mealDao.getNextMealId();

        mealDao.saveMeal(meal, mealId);
        mealDao.saveIngredients(meal.getIngredients(), mealId);

        List<Meal> meals = mealDao.getAllMealsByCategory("breakfast");

        assertEquals(1, meals.size());
        assertEquals("Omelette", meals.get(0).getNameOfMeal());
        assertEquals(3, meals.get(0).getIngredients().size());
        assertTrue(meals.get(0).getIngredients().contains("eggs"));
    }

 */
}
