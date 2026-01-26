package org.example.app;

import org.example.core.dao.DatabaseCleaner;
import org.example.core.dao.DatabaseSeeder;
import org.example.core.dao.MealDao;
import org.example.core.dao.PlanDao;
import org.example.core.model.*;
import org.example.core.service.PlanService;
import org.example.core.service.ShoppingListService;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    private final Connection connection;
    private final MealDao mealDao;
    private final PlanDao planDao;
    private final PlanService planService;
    private final ShoppingListService shoppingListService;
    private final Scanner scan;

    public ConsoleApp(
            Connection connection,
            MealDao mealDao,
            PlanDao planDao,
            PlanService planService,
            ShoppingListService shoppingListService,
            Scanner scan
    ) {
        this.connection = connection;
        this.mealDao = mealDao;
        this.planDao = planDao;
        this.planService = planService;
        this.shoppingListService = shoppingListService;
        this.scan = scan;
    }

    public void run() throws SQLException {
        String command;

        do {
            printMenu();
            command = scan.nextLine();

            switch (command) {
                case "add" -> addMeal();
                case "show" -> showMeals();
                case "plan" -> planMeals();
                case "list plan" -> showPlan();
                case "save" -> saveShoppingList();
                case "reset" -> resetDatabase();
                case "exit" -> System.out.println("Bye!");
                default -> System.out.println("Unknown command.");
            }

        } while (!command.equals("exit"));
    }

    private void printMenu() {
        System.out.println(
                "What would you like to do (add, show, plan, list plan, save, reset, exit)?"
        );
    }

    private void addMeal() throws SQLException {
        Meal meal = Meal.addNewMeal(scan);
        int mealId = mealDao.getNextMealId();
        mealDao.saveMeal(meal, mealId);
        mealDao.saveIngredients(meal.getIngredients(), mealId);
    }

    private void showMeals() throws SQLException {
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        String mealCategory = readMealCategory();

        List<Meal> meals = mealDao.getAllMealsByCategory(mealCategory);

        if (meals.isEmpty()) {
            System.out.println("No meals found.");
            return;
        }

        System.out.println("Category: " + mealCategory);
        meals.forEach(Meal::printMealInfo);
        System.out.println();
    }

    private void planMeals() throws SQLException {
        planService.clearPlan();

        for (DaysOfTheWeek day : DaysOfTheWeek.values()) {
            System.out.println(day);

            for (String category : List.of("breakfast", "lunch", "dinner")) {

                List<Meal> meals = planService.getMealsByCategory(category);
                meals.forEach(m -> System.out.println(m.getNameOfMeal()));

                System.out.println("Choose the " + category + " for " + day + ":");
                Meal chosenMeal = chooseMeal(meals);

                planService.addPlanItem(day, category, chosenMeal);
            }

            System.out.println("Meals planned for " + day + ".\n");
        }
    }

    private Meal chooseMeal(List<Meal> meals) {
        while (true) {
            String input = scan.nextLine();

            for (Meal meal : meals) {
                if (meal.getNameOfMeal().equals(input)) {
                    return meal;
                }
            }

            System.out.println("This meal doesn’t exist. Choose again.");
        }
    }

    private void showPlan() throws SQLException {
        List<MealPlan> plans = planDao.getWeeklyPlan();

        if (plans.isEmpty()) {
            System.out.println("Database does not contain any meal plans");
            return;
        }

        for (MealPlan plan : plans) {
            System.out.println(plan.getDay());
            System.out.println("Breakfast: " + plan.getBreakfast());
            System.out.println("Lunch: " + plan.getLunch());
            System.out.println("Dinner: " + plan.getDinner());
            System.out.println();
        }
    }

    private void saveShoppingList() throws SQLException {
        if (planDao.getWeeklyPlan().isEmpty()) {
            System.out.println("Unable to save. Plan your meals first.");
            return;
        }

        System.out.println("Input a filename:");
        String filename = scan.nextLine();

        List<Ingredient> ingredients = shoppingListService.getShoppingList();

        try (FileWriter writer = new FileWriter(filename)) {
            for (Ingredient ingredient : ingredients) {
                writer.write(ingredient.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write shopping list", e);
        }

        System.out.println("Saved!");
    }

    private void resetDatabase() throws SQLException {
        DatabaseCleaner.clearAll(connection);
        DatabaseSeeder.seed(mealDao);
        System.out.println("Database reset.");
    }

    private String readMealCategory() {
        while (true) {
            String input = scan.nextLine();
            if (input.equals("breakfast") ||
                    input.equals("lunch") ||
                    input.equals("dinner")) {
                return input;
            }
            System.out.println("Wrong category! Choose: breakfast, lunch, dinner.");
        }
    }
}



