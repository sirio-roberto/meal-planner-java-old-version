package mealplanner;

import mealplanner.model.Ingredient;
import mealplanner.model.Meal;

import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        startProgram();
    }

    private static void startProgram() {
        Map<String, Meal> categories = new HashMap<>();

        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        String category = scan.nextLine();

        System.out.println("Input the meal's name:");
        String mealName = scan.nextLine();

        System.out.println("Input the ingredients:");
        String[] ingredientsStr = scan.nextLine().split(",");

        List<Ingredient> ingredients = Arrays.stream(ingredientsStr)
                .map(Ingredient::new)
                .toList();

        Meal meal = new Meal(mealName, ingredients);
        categories.put(category, meal);
        System.out.println();

        for (String cat: categories.keySet()) {
            System.out.printf("Category: %s%n", cat);
            System.out.printf("Name: %s%n", categories.get(cat).getName());
            System.out.println("Ingredients:");
            for (Ingredient ingredient: categories.get(cat).getIngredients()) {
                System.out.println(ingredient.getName());
            }
        }
        System.out.println("The meal has been added!");
    }
}