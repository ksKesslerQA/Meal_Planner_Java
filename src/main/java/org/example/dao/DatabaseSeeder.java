package org.example.dao;

import org.example.model.Ingredient;
import org.example.model.Meal;
import org.example.model.Unit;

import java.sql.SQLException;
import java.util.List;

public class DatabaseSeeder {

    //DatabaseSeeder.seed(mealDao);

    public static void seed(MealDao mealDao) throws SQLException {
        addMeal(mealDao, "breakfast", "oatmeal with apples",
                List.of(
                        new Ingredient("oats", 40, Unit.GRAMS),
                        new Ingredient("apple", 1, Unit.PCS),
                        new Ingredient("walnut", 20, Unit.GRAMS)
                ));

        addMeal(mealDao, "lunch", "shakshuka",
                List.of(
                        new Ingredient("eggs", 4, Unit.PCS),
                        new Ingredient("canned tomatoes", 1, Unit.PCS),
                        new Ingredient("onion", 1, Unit.PCS)
                ));

        addMeal(mealDao, "dinner", "garlic bread",
                List.of( new Ingredient("frozen garlic bread", 1, Unit.PCS)));
    }

    private static void addMeal(MealDao dao, String category, String name,
                                List<Ingredient> ingredients) throws SQLException {

        int id = dao.getNextMealId();
        dao.saveMeal(new Meal(category, name, ingredients), id);
        dao.saveIngredients(ingredients, id);
    }
}

