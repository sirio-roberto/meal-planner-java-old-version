package mealplanner;

import mealplanner.model.Ingredient;
import mealplanner.model.Meal;
import mealplanner.model.enums.Category;
import mealplanner.utils.DBUtil;
import mealplanner.utils.InputValidator;

import java.sql.Connection;
import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);

    static HashSet<Meal> meals = new LinkedHashSet<>();

    public static void main(String[] args) {
        DBUtil.createRequiredTables();
        // meals = DBUtil.getAllMeals();
        showMainMenu();
    }

    private static void showMainMenu() {
        String userOption;

        do {
            System.out.println("What would you like to do (add, show, plan, exit)?");
            userOption = scan.nextLine();

            switch (userOption) {
                case "add" -> addMeal();
                case "show" -> showMeals();
                case "exit" -> System.out.println("Bye!");
            }

        } while (!"exit".equals(userOption));
    }

    private static void showMeals() {
        System.out.println("Which category do you want to print (breakfast, lunch, dinner)?");
        String categoryName = scan.nextLine();
        while (!InputValidator.isValidCategoryName(categoryName)) {
            categoryName = scan.nextLine();
        }
        meals = DBUtil.getMealsByCategory(categoryName);
        if (!meals.isEmpty()) {
            System.out.printf("Category: %s%n", categoryName);
            System.out.println();
            for (Meal meal : meals) {
                System.out.printf("Name: %s%n", meal.getName());
                System.out.println("Ingredients:");
                for (Ingredient ingredient : meal.getIngredients()) {
                    System.out.println(ingredient.getName());
                }
                System.out.println();
            }
        } else {
            System.out.println("No meals found.");
        }
    }

    private static void addMeal() {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        String categoryName = scan.nextLine();
        while (!InputValidator.isValidCategoryName(categoryName)) {
            categoryName = scan.nextLine();
        }
        Category category = Category.getCategoryByValue(categoryName);

        System.out.println("Input the meal's name:");
        String mealName = scan.nextLine();
        while (!InputValidator.isValidName(mealName)) {
            mealName = scan.nextLine();
        }

        System.out.println("Input the ingredients:");
        String[] ingredientsStr = scan.nextLine().split(",");
        while (!InputValidator.areAllValidNames(ingredientsStr)) {
            ingredientsStr = scan.nextLine().split(",");
        }

        List<Ingredient> ingredients = Arrays.stream(ingredientsStr)
                .map(String::trim)
                .map(Ingredient::new)
                .toList();

        Meal meal = new Meal(mealName, category, ingredients);
        meals.add(meal);

        DBUtil.insertIntoMeals(meal);
        insertIngredientsToDB(ingredients, meal);

        System.out.println("The meal has been added!");
    }

    private static void insertIngredientsToDB(List<Ingredient> ingredients, Meal linkedMeal) {
        ingredients.forEach(i -> i.setMealId(linkedMeal.getId()));
        ingredients.forEach(DBUtil::insertIntoIngredients);
    }
}