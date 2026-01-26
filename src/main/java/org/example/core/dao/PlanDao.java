package org.example.core.dao;

import org.example.core.model.Ingredient;
import org.example.core.model.MealPlan;
import org.example.core.model.Unit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {

    private final Connection connection;

    public PlanDao(Connection connection) {
        this.connection = connection;
    }

    public void clearPlan() throws SQLException {
        Statement st = connection.createStatement();
        st.executeUpdate("DELETE FROM plan");
        st.close();
    }

    public void savePlanItem(String day, String category, String mealName, int mealId) throws SQLException {
       String sql = """
               INSERT INTO plan (day, meal_category, meal_option, meal_id)
               VALUES (?, ?, ?, ?)
               """;

       PreparedStatement ps = connection.prepareStatement(sql);
       ps.setString(1, day);
       ps.setString(2, category);
       ps.setString(3, mealName);
       ps.setInt(4, mealId);
       ps.executeUpdate();
       ps.close();
    }

    public List<MealPlan> getWeeklyPlan() throws SQLException {
        String sql = """
                 SELECT day,
                                   MAX(CASE WHEN meal_category = 'breakfast' THEN meal_option END) AS breakfast,
                                   MAX(CASE WHEN meal_category = 'lunch' THEN meal_option END) AS lunch,
                                   MAX(CASE WHEN meal_category = 'dinner' THEN meal_option END) AS dinner
                            FROM plan
                            GROUP BY day
                            ORDER BY CASE day
                                            WHEN 'Monday' THEN 1
                                            WHEN 'Tuesday' THEN 2
                                            WHEN 'Wednesday' THEN 3
                                            WHEN 'Thursday' THEN 4
                                            WHEN 'Friday' THEN 5
                                            WHEN 'Saturday' THEN 6
                                            WHEN 'Sunday' THEN 7
                                        END
                """;

        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<MealPlan> plans = new ArrayList<>();

        while(rs.next()) {
            plans.add(new MealPlan(
                rs.getString("day"),
                        rs.getString("breakfast"),
                        rs.getString("lunch"),
                        rs.getString("dinner")
            ));
            }

            rs.close();
            st.close();
            return plans;
        }


    public List<Ingredient> getPlannedIngredients() throws SQLException {
        String sql = """
                SELECT i.name,
                       SUM(i.amount) AS total_amount,
                       i.unit
                FROM ingredients i
                JOIN plan p ON p.meal_id = i.meal_id
                GROUP BY i.name, i.unit
                ORDER BY i.name
                """;

        List<Ingredient> ingredients = new ArrayList<>();

        try (
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);
        ) {
            while (rs.next()) {
               ingredients.add(new Ingredient(
                        rs.getString("name"),
                        rs.getDouble("total_amount"),
                        Unit.fromDb(rs.getString("unit"))
                ));
            }
        }

        return ingredients;
    }
}