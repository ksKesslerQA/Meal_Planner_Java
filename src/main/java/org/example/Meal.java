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

    static void addNewMeal(){
            Scanner scan = new Scanner(System.in);

        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        String mealCategory = scan.nextLine();

        System.out.println("Input the meal's name:");
        String nameOfMeal = scan.nextLine();

        System.out.println("Input the ingredients:");
        String ingredientsInput = scan.nextLine();

        List<String> ingredients = Arrays.stream(ingredientsInput.split(","))
                .map(String::trim)
                .toList();

        Meal addedMeal = new Meal(mealCategory, nameOfMeal, ingredients);

        printMealInfo(addedMeal);
        System.out.println("The meal has been added!");

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
