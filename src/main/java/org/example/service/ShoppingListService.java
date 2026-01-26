package org.example.service;

import org.example.dao.PlanDao;
import org.example.model.Ingredient;
import org.example.util.UnitConverter;

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
