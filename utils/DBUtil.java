package mealplanner.utils;

import mealplanner.db.Database;
import mealplanner.model.Ingredient;
import mealplanner.model.Meal;
import mealplanner.model.enums.Category;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class DBUtil {

    static final String CREATE_MEALS = """
                    CREATE TABLE IF NOT EXISTS meals (
                        meal_id integer, 
                        meal varchar(50), 
                        category varchar(20) 
                    );""";

    static final String CREATE_INGREDIENTS = """
                    CREATE TABLE IF NOT EXISTS ingredients (
                        ingredient_id integer, 
                        ingredient varchar(50), 
                        meal_id integer 
                    );""";

    static final String SELECT_MEALS = "SELECT * FROM meals;";

    static final String SELECT_MEALS_BY_CATEGORY = "SELECT * FROM meals WHERE category = ?;";

    static final String SELECT_INGREDIENTS = "SELECT * FROM ingredients;";

    static final String INSERT_MEALS = "INSERT INTO meals (meal_id, meal, category) VALUES (?, ?, ?);";

    static final String INSERT_INGREDIENTS = "INSERT INTO ingredients (ingredient_id, ingredient, meal_id) VALUES (?, ?, ?);";

    static Connection conn;
    public static void createRequiredTables() {
        conn = Database.getConnection();

        createMealsTable();
        createIngredientsTable();
    }

    private static void createMealsTable() {
        Statement st = null;
        try  {
            st = conn.createStatement();
            st.executeUpdate(CREATE_MEALS);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeStatement(st);
        }
    }

    private static void createIngredientsTable() {
        Statement st = null;
        try  {
            st = conn.createStatement();
            st.executeUpdate(CREATE_INGREDIENTS);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeStatement(st);
        }
    }

    public static HashSet<Meal> getAllMeals() {
        HashSet<Meal> meals = new LinkedHashSet<>();
        HashSet<Ingredient> ingredients = getAllIngredients();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(SELECT_MEALS);
            while (rs.next()) {
                Integer id = rs.getInt("meal_id");
                String name = rs.getString("meal");
                String categoryName = rs.getString("category");

                Category category = Category.getCategoryByValue(categoryName);
                List<Ingredient> mealIngredients = ingredients.stream()
                        .filter(i -> Objects.equals(i.getMealId(), id))
                        .toList();

                Meal meal = new Meal(id, name, category, mealIngredients);
                meals.add(meal);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
        return meals;
    }

    private static HashSet<Ingredient> getAllIngredients() {
        HashSet<Ingredient> ingredients = new LinkedHashSet<>();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(SELECT_INGREDIENTS);
            while (rs.next()) {
                Integer id = rs.getInt("ingredient_id");
                String name = rs.getString("ingredient");
                Integer mealId = rs.getInt("meal_id");

                Ingredient ingredient = new Ingredient(id, name, mealId);
                ingredients.add(ingredient);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
        return ingredients;
    }

    public static void insertIntoMeals(Meal meal) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(INSERT_MEALS, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, meal.getId());
            st.setString(2, meal.getName());
            st.setString(3, meal.getCategory().getValue());

            st.executeUpdate();
            rs = st.getGeneratedKeys();
            /*
            if (rs.next()) {
                meal.setId(rs.getInt("meal_id"));
            }
            */
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
    }

    public static void insertIntoIngredients(Ingredient ingredient) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(INSERT_INGREDIENTS, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, ingredient.getId());
            st.setString(2, ingredient.getName());
            st.setInt(3, ingredient.getMealId());

            st.executeUpdate();
            rs = st.getGeneratedKeys();
            /*
            if (rs.next()) {
                ingredient.setId(rs.getInt("ingredient_id"));
            }
             */
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
    }

    public static HashSet<Meal> getMealsByCategory(String providedCategory) {
        HashSet<Meal> meals = new LinkedHashSet<>();
        HashSet<Ingredient> ingredients = getAllIngredients();
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(SELECT_MEALS_BY_CATEGORY);
            st.setString(1, providedCategory);

            rs = st.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("meal_id");
                String name = rs.getString("meal");
                String categoryName = rs.getString("category");

                Category category = Category.getCategoryByValue(categoryName);
                List<Ingredient> mealIngredients = ingredients.stream()
                        .filter(i -> Objects.equals(i.getMealId(), id))
                        .toList();

                Meal meal = new Meal(id, name, category, mealIngredients);
                meals.add(meal);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeResultSet(rs);
            Database.closeStatement(st);
        }
        return meals;
    }
}
