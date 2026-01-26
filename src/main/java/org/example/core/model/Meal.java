package org.example.core.model;

import org.example.core.util.IngredientParser;

import java.util.List;
import java.util.Scanner;

public class Meal {
    private String mealCategory;
    private String nameOfMeal;
    private List<Ingredient> ingredients;

    public Meal(String mealCategory, String nameOfMeal, List<Ingredient> ingredients) {
        this.mealCategory = mealCategory;
        this.nameOfMeal = nameOfMeal;
        this.ingredients = ingredients;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public String getNameOfMeal() {
        return nameOfMeal;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public static Meal addNewMeal(Scanner scan){

        String mealCategory;
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");

        while (true) {
            mealCategory = scan.nextLine();

            if (mealCategory.equals("breakfast") ||
                    mealCategory.equals("lunch") ||
                    mealCategory.equals("dinner")) {
                break;
            } else {
                System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            }
        }

        String nameOfMeal;
        System.out.println("Input the meal's name:");

        while (true) {
            nameOfMeal = scan.nextLine();

            if (nameOfMeal.matches("[a-zA-Z ]+")) {
                break;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }

        System.out.println("Input ingredients (format: name amount unit, separated by commas):");
        System.out.println("Example: eggs 2 pcs, milk 200 ml, flour 100 g");

        String input = scan.nextLine();
        List<Ingredient> ingredients = IngredientParser.parse(input);
/*
        while (true) {
            String ingredientsInput = scan.nextLine();

            List<String> tempIngredients = Arrays.stream(ingredientsInput.split(","))
                    .map(String::trim)
                    .toList();

            boolean allValid = true;

            for (String ingredient : tempIngredients) {
                if (!ingredient.matches("[a-zA-Z ]+")) {
                    allValid = false;
                    break;
                }
            }

            if (allValid) {
                ingredients = tempIngredients;
                break;
            } else {
                System.out.println("Wrong format. Use letters only!");
            }
        }

 */

        System.out.println("The meal has been added!");

        return new Meal(mealCategory, nameOfMeal, ingredients);

    };

    public static void printMealInfo(Meal meal) {
        System.out.println("\nName: " + meal.getNameOfMeal());
        System.out.println("Ingredients:");
        meal.getIngredients().forEach(i -> System.out.println(i));
    }
}
