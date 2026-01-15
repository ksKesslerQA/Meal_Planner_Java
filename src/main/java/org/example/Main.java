package org.example;



import java.util.ArrayList;
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
        connection.setAutoCommit(true);

        DatabaseInitializer.createTables(connection);

        Scanner scan = new Scanner(System.in);
        List<Meal> meals = new ArrayList<>();

        String operation = "";
        while(!operation.equals("exit")){
            System.out.println("What would you like to do (add, show, exit)?");
            operation = scan.nextLine();

            if(operation.equals("add")){
                Meal meal = Meal.addNewMeal(scan);
                meals.add(meal);
            } else if (operation.equals("show")) {
                    if(meals.isEmpty()){
                        System.out.println("No meals saved. Add a meal first.");
                    } else {

                        for (var meal : meals) {
                            Meal.printMealInfo(meal);
                        }
                    }
            } else if (operation.equals("exit")){
                System.out.println("Bye!");
            } else {
                continue;
            }
        }

    }
}