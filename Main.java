package mealplanner;

import mealplanner.model.Ingredient;
import mealplanner.model.Meal;
import mealplanner.model.Plan;
import mealplanner.model.enums.Category;
import mealplanner.model.enums.Weekday;
import mealplanner.utils.DBUtil;
import mealplanner.utils.InputValidator;
import mealplanner.utils.StringUtils;

import java.sql.Connection;
import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);

    static HashSet<Meal> meals = new LinkedHashSet<>();

    static HashSet<Plan> plans = new LinkedHashSet<>();

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
                case "plan" -> planWeek();
                case "exit" -> System.out.println("Bye!");
            }

        } while (!"exit".equals(userOption));
    }

    private static void planWeek() {
        createPlans();
        showPlans();
    }

    private static void createPlans() {
        for (Weekday weekday: Weekday.values()) {
            System.out.println(weekday.getValue());
            for (Category category: Category.values()) {
                List<Meal> categoryMeals = DBUtil.getMealsByCategory(category.getValue()).stream().sorted().toList();
                categoryMeals.forEach(m -> System.out.println(m.getName()));
                System.out.printf("Choose the %s for %s from the list above:%n", category.getValue(), weekday.getValue());
                String userChoice = scan.nextLine();

                Meal chosenMeal = categoryMeals.stream()
                        .filter(m -> m.getName().equals(userChoice)).findAny().orElse(null);
                while (chosenMeal == null) {
                    System.out.println("This meal doesn’t exist. Choose a meal from the list above.");
                    final String finalUserChoice = scan.nextLine();
                    chosenMeal = categoryMeals.stream()
                            .filter(m -> m.getName().equals(finalUserChoice)).findAny().orElse(null);
                }
                Plan plan = new Plan(chosenMeal, weekday.getValue());
                plans.add(plan);
                DBUtil.insertIntoPlan(plan);
            }
            System.out.printf("Yeah! We planned the meals for %s.%n", weekday.getValue());
            System.out.println();
        }
    }

    private static void showPlans() {
        for (Weekday weekday: Weekday.values()) {
            System.out.println(weekday.getValue());
            for(Plan subPlan: plans.stream().filter(p -> p.getWeekDay().equals(weekday.getValue())).toList()) {
                Meal meal = subPlan.getMeal();
                System.out.printf("%s: %s%n",
                        StringUtils.convertToTitleCaseIteratingChars(meal.getCategory().getValue()),
                        meal.getName());
            }
            System.out.println();
        }
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