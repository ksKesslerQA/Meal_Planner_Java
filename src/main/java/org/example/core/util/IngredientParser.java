package org.example.core.util;

import org.example.core.model.Ingredient;
import org.example.core.model.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IngredientParser {

    public static List<Ingredient> parse(String input) {
        List<Ingredient> result = new ArrayList<>();

        String[] parts = input.split(",");

        for (String part : parts) {
            String[] tokens = part.trim().split("\\s+");
            String unit = tokens[tokens.length - 1];
            double amount = Double.parseDouble(tokens[tokens.length - 2]);

            String name = String.join(" ",
                    Arrays.copyOf(tokens, tokens.length - 2));

            Unit parsedUnit = Unit.fromUserInput(unit);
            result.add(new Ingredient(name, amount, parsedUnit));
        }

        return result;
    }
}
