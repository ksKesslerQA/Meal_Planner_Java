package org.example;

import org.example.app.ConsoleApp;
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

        DatabaseInitializer.createTables(connection);

        MealDao mealDao = new MealDao(connection);
        PlanDao planDao = new PlanDao(connection);

        PlanService planService = new PlanService(mealDao, planDao);
        ShoppingListService shoppingListService = new ShoppingListService(planDao);

        Scanner scan = new Scanner(System.in);

        ConsoleApp app = new ConsoleApp(
                connection,
                mealDao,
                planDao,
                planService,
                shoppingListService,
                scan
        );

        app.run();


    }
}