package org.example;

import org.example.dao.*;
import org.example.model.DaysOfTheWeek;
import org.example.model.Meal;
import org.example.model.MealPlan;
import org.example.service.PlanService;
import org.example.service.ShoppingListService;

import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        String DB_URL = "jdbc:postgresql:meals_db";
        String USER = "postgres";
        String PASS = "root";

        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        MealDao mealDao = new MealDao(connection);
        PlanDao planDao = new PlanDao(connection);
        PlanService planService = new PlanService(mealDao, planDao);
        ShoppingListService shoppingListService = new ShoppingListService(planDao);
        connection.setAutoCommit(true);

        DatabaseInitializer.createTables(connection);

        Scanner scan = new Scanner(System.in);

        String operation = "";
        while(!operation.equals("exit")) {
            System.out.println("What would you like to do (add, show, plan, list plan, save, reset, exit)?");
            operation = scan.nextLine();

            if (operation.equals("add")) {
                Meal meal = Meal.addNewMeal(scan);

                int mealId = mealDao.getNextMealId();
                mealDao.saveMeal(meal, mealId);
                mealDao.saveIngredients(meal.getIngredients(), mealId);
            } else if (operation.equals("show")) {
                // List<Meal> mealsFromDb = mealDao.getAllMeals();
                String mealCategory;
                System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
                while (true) {
                    mealCategory = scan.nextLine();

                    if (mealCategory.equals("breakfast") ||
                            mealCategory.equals("lunch") ||
                            mealCategory.equals("dinner")) {
                        break;
                    } else {
                        System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
                    }
                }
                List<Meal> mealsFromDbByCategory = mealDao.getAllMealsByCategory(mealCategory);

                if (mealsFromDbByCategory.isEmpty()) {
                    System.out.println("No meals found.");
                } else {
                    System.out.println("Category: " + mealCategory);

                    for (Meal meal : mealsFromDbByCategory) {
                        Meal.printMealInfo(meal);
                    }
                    System.out.println();
                }
            } else if (operation.equals("plan")) {
                planService.createWeeklyPlan(scan);

            } else if (operation.equals("list plan")) {
                List<MealPlan> plans = planDao.getWeeklyPlan();

                if (plans.isEmpty()) {
                    System.out.println("Database does not contain any meal plans");
                } else {
                    for (MealPlan plan : plans) {
                        System.out.println(plan.getDay());
                        System.out.println("Breakfast: " + plan.getBreakfast());
                        System.out.println("Lunch: " + plan.getLunch());
                        System.out.println("Dinner: " + plan.getDinner());
                        System.out.println();
                    }
                }

            } else if (operation.equals("save")) {
                if (planDao.getWeeklyPlan().isEmpty()) {
                    System.out.println("Unable to save. Plan your meals first.");
                } else {
                    System.out.println("Input a filename:");
                    String filename = scan.nextLine();

                    shoppingListService.saveShoppingList(filename);

                    System.out.println("Saved!");
                }

            } else if (operation.equals("reset")) {

                DatabaseCleaner.clearAll(connection);
                DatabaseSeeder.seed(mealDao);
                System.out.println("Database reset.");

            } else if (operation.equals("exit")) {
                System.out.println("Bye!");
            } else {
                continue;
            }
        }

    }
}