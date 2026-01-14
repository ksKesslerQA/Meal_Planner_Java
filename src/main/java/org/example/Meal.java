package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Meal {
    private String mealCategory;
    private String nameOfMeal;
    private List<String> ingredients;

    public Meal(String mealCategory, String nameOfMeal, List<String> ingredients) {
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

    public List<String> getIngredients() {
        return ingredients;
    }

    static Meal addNewMeal(Scanner scan){

        String mealCategory;
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");

        while (true) {
            mealCategory = scan.nextLine();

            if (mealCategory.equals("breakfast") ||
                    mealCategory.equals("lunch") ||
                    mealCategory.equals("dinner")) {
                break; // вихід з циклу, якщо все ок
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

        List<String> ingredients;
        System.out.println("Input the ingredients:");

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

        Meal addedMeal = new Meal(mealCategory, nameOfMeal, ingredients);

        //printMealInfo(addedMeal);
        System.out.println("The meal has been added!");

        return addedMeal;

    };

    static void printMealInfo(Meal meal){
        System.out.println("\nCategory: " + meal.getMealCategory());
        System.out.println("Name: " + meal.getNameOfMeal());
        System.out.println("Ingredients:");

        for (String ingredient : meal.getIngredients()) {
            System.out.println(ingredient);
        }

    }
}
