package org.example.service;

import org.example.dao.MealDao;
import org.example.dao.PlanDao;
import org.example.model.DaysOfTheWeek;
import org.example.model.Meal;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class PlanService {

    private final MealDao mealDao;
    private final PlanDao planDao;

    public PlanService(MealDao mealDao, PlanDao planDao) {
        this.mealDao = mealDao;
        this.planDao = planDao;
    }

    public void createWeeklyPlan(Scanner scan) throws SQLException {

        planDao.clearPlan();

        for (DaysOfTheWeek day : DaysOfTheWeek.values()) {
            System.out.println(day);

            for (String category : List.of("breakfast", "lunch", "dinner")) {

                List<Meal> mealsByCategory = mealDao.getAllMealsByCategory(category);
                mealsByCategory.forEach(m -> System.out.println(m.getNameOfMeal()));

                System.out.println("Choose the " + category + " for " + day + " from the list above:");

                Meal chosenMeal = chooseMeal(scan, mealsByCategory);
                int mealId = mealDao.getMealIdByName(chosenMeal.getNameOfMeal());

                planDao.savePlanItem(day.name(), category, chosenMeal.getNameOfMeal(), mealId);
            }

            System.out.println("Yeah! We planned the meals for " + day + ".\n");
        }

    }

    private Meal chooseMeal(Scanner scan, List<Meal> meals) {
        while (true) {
            String input = scan.nextLine();
            return meals.stream()
                    .filter(m -> m.getNameOfMeal().equals(input))
                    .findFirst()
                    .orElseGet(() -> {
                        System.out.println("This meal doesn’t exist. Choose again.");
                        return null;
                    });
        }
    }
}

