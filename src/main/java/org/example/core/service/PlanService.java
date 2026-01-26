package org.example.core.service;

import org.example.core.dao.MealDao;
import org.example.core.dao.PlanDao;
import org.example.core.model.DaysOfTheWeek;
import org.example.core.model.Meal;

import java.sql.SQLException;
import java.util.List;

public class PlanService {

    private final MealDao mealDao;
    private final PlanDao planDao;

    public PlanService(MealDao mealDao, PlanDao planDao) {
        this.mealDao = mealDao;
        this.planDao = planDao;
    }

    public void clearPlan() throws SQLException {
        planDao.clearPlan();
    }

    public List<Meal> getMealsByCategory(String category) throws SQLException {
        return mealDao.getAllMealsByCategory(category);
    }

    public void addPlanItem(
            DaysOfTheWeek day,
            String category,
            Meal meal
    ) throws SQLException {

        int mealId = mealDao.getMealIdByName(meal.getNameOfMeal());
        planDao.savePlanItem(
                day.name(),
                category,
                meal.getNameOfMeal(),
                mealId
        );
    }
}

