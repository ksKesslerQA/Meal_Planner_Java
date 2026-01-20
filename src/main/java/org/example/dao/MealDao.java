package org.example.dao;

import org.example.model.Ingredient;
import org.example.model.Meal;
import org.example.model.Unit;

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


    public void saveIngredients(List<Ingredient> ingredients, int mealId) throws SQLException {
        String sql = "INSERT INTO ingredients (name, amount, unit, meal_id) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        for (Ingredient ingredient : ingredients) {
            ps.setString(1, ingredient.getName());
            ps.setDouble(2, ingredient.getAmount());
            ps.setString(3, ingredient.getUnit().name());
            ps.setInt(4, mealId);
            ps.executeUpdate();
        }

        ps.close();
    }

    public List<Meal> getAllMeals() throws SQLException {
        String sql = """
        SELECT m.meal_id,
               m.category,
               m.meal,
               i.name,
               i.amount,
               i.unit
        FROM meals m
        JOIN ingredients i ON m.meal_id = i.meal_id
        ORDER BY m.meal, i.ingredient_id
        """;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        List<Meal> meals = new ArrayList<>();
        int currentMealId = -1;
        Meal currentMeal = null;
        List<Ingredient> ingredients = null;

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

            ingredients.add(new Ingredient(
                    rs.getString("name"),
                    rs.getDouble("amount"),
                    Unit.valueOf(rs.getString("unit"))
            ));
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
        SELECT m.meal_id,
               m.category,
               m.meal,
               i.name,
               i.amount,
               i.unit
        FROM meals m
        JOIN ingredients i ON m.meal_id = i.meal_id
        WHERE m.category = ?
        ORDER BY m.meal, i.ingredient_id
        """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, category);

        ResultSet rs = ps.executeQuery();

        List<Meal> meals = new ArrayList<>();
        int currentMealId = -1;
        Meal currentMeal = null;
        List<Ingredient> ingredients = null;

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

            ingredients.add(new Ingredient(
                    rs.getString("name"),
                    rs.getDouble("amount"),
                    Unit.valueOf(rs.getString("unit"))
            ));
        }

        if (currentMeal != null) {
            meals.add(currentMeal);
        }

        rs.close();
        ps.close();

        return meals;
    }



    public int getMealIdByName(String nameOfMeal) throws SQLException {
        String sql = """
        SELECT meal_id
        FROM meals
        WHERE meal = ?
        """;

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nameOfMeal);

        ResultSet rs = statement.executeQuery();
        int mealId = -1;

        if (rs.next()) {
            mealId = rs.getInt("meal_id");
        }

        if (mealId == -1) {
            throw new SQLException("Meal not found: " + nameOfMeal);
        }

        rs.close();
        statement.close();

        return mealId;
    }
}
