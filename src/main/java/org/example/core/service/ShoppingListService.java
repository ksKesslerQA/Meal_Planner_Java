package org.example.core.service;

import org.example.core.dao.PlanDao;
import org.example.core.model.Ingredient;
import org.example.core.util.UnitConverter;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingListService {

    private final PlanDao planDao;

    public ShoppingListService(PlanDao planDao) {
        this.planDao = planDao;
    }

    public List<Ingredient> getShoppingList() throws SQLException {
        return planDao.getPlannedIngredients()
                .stream()
                .map(UnitConverter::normalize)
                .collect(Collectors.toList());
    }
}
