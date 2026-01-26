package org.example.core.service;

import org.example.core.dao.PlanDao;
import org.example.core.model.Ingredient;
import org.example.core.util.UnitConverter;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShoppingListService {

    private final PlanDao planDao;

    public ShoppingListService(PlanDao planDao) {
        this.planDao = planDao;
    }

    public void saveShoppingList(String filename) throws SQLException {
        List<Ingredient> ingredients = planDao.getPlannedIngredients();

        try (FileWriter writer = new FileWriter(filename)) {
            for (Ingredient ingredient : ingredients) {
                Ingredient normalized = UnitConverter.normalize(ingredient);
                writer.write(normalized.toString() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write shopping list", e);
        }
    }

}
