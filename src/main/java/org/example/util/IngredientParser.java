package org.example.util;

import org.example.model.Ingredient;
import org.example.model.Unit;

import java.util.ArrayList;
import java.util.List;

public class IngredientParser {

    public static List<Ingredient> parse(String input) {
        List<Ingredient> result = new ArrayList<>();

        String[] parts = input.split(",");

        for (String part : parts) {
            String[] tokens = part.trim().split(" ");

            if (tokens.length != 3) {
                throw new IllegalArgumentException(
                        "Wrong format. Use: name amount unit"
                );
            }

            String name = tokens[0];
            double amount = Double.parseDouble(tokens[1]);
            Unit unit = Unit.fromString(tokens[2]);

            result.add(new Ingredient(name, amount, unit));
        }

        return result;
    }
}
