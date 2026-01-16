package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealDao {

    private final Connection connection;

    public MealDao(Connection connection) {
        this.connection = connection;
    }

    public int getNextMealId() throws SQLException {
        String sql = "SELECT MAX(meal_id) FROM meals";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        int nextId = 1;
        if (rs.next() && rs.getInt(1) != 0) {
            nextId = rs.getInt(1) + 1;
        }

        rs.close();
        statement.close();
        return nextId;
    }

    public void saveMeal(Meal meal, int mealId) throws SQLException {
        String sql = "INSERT INTO meals (meal_id, category, meal) VALUES (?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, mealId);
        ps.setString(2, meal.getMealCategory());
        ps.setString(3, meal.getNameOfMeal());

        ps.executeUpdate();
        ps.close();
    }


    public void saveIngredients(List<String> ingredients, int mealId) throws SQLException {
        String sql = "INSERT INTO ingredients (ingredient, meal_id) VALUES (?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        for (String ingredient : ingredients) {
            ps.setString(1, ingredient);
            ps.setInt(2, mealId);
            ps.executeUpdate();
        }

        ps.close();
    }

    public List<Meal> getAllMeals() throws SQLException {
        String sql = """
        SELECT m.meal_id, m.category, m.meal, i.ingredient
        FROM meals m
        JOIN ingredients i ON m.meal_id = i.meal_id
        ORDER BY m.meal_id
        """;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        List<Meal> meals = new ArrayList<>();

        int currentMealId = -1;
        Meal currentMeal = null;
        List<String> ingredients = null;

        while (rs.next()) {
            int mealId = rs.getInt("meal_id");

            if (mealId != currentMealId) {
                if (currentMeal != null) {
                    meals.add(currentMeal);
                }

                ingredients = new ArrayList<>();
                currentMeal = new Meal(
                        rs.getString("category"),
                        rs.getString("meal"),
                        ingredients
                );

                currentMealId = mealId;
            }

            ingredients.add(rs.getString("ingredient"));
        }

        if (currentMeal != null) {
            meals.add(currentMeal);
        }

        rs.close();
        statement.close();

        return meals;
    }

    public List<Meal> getAllMealsByCategory(String category) throws SQLException {

        String sql = """
        SELECT m.meal_id, m.category, m.meal, i.ingredient
        FROM meals m
        JOIN ingredients i ON m.meal_id = i.meal_id
        WHERE m.category = ?
        ORDER BY m.meal_id, i.ingredient_id
        """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, category);

        ResultSet rs = statement.executeQuery();

        List<Meal> meals = new ArrayList<>();

        int currentMealId = -1;
        Meal currentMeal = null;
        List<String> ingredients = null;

        while (rs.next()) {
            int mealId = rs.getInt("meal_id");

            if (mealId != currentMealId) {
                if (currentMeal != null) {
                    meals.add(currentMeal);
                }

                ingredients = new ArrayList<>();
                currentMeal = new Meal(
                        rs.getString("category"),
                        rs.getString("meal"),
                        ingredients
                );

                currentMealId = mealId;
            }

            ingredients.add(rs.getString("ingredient"));
        }

        if (currentMeal != null) {
            meals.add(currentMeal);
        }

        rs.close();
        statement.close();

        return meals;
    }




}
