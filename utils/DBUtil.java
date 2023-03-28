package mealplanner.utils;

import mealplanner.db.Database;
import mealplanner.model.Ingredient;
import mealplanner.model.Meal;
import mealplanner.model.enums.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

public class DBUtil {

    static final String CREATE_MEALS = """
                    CREATE TABLE IF NOT EXISTS meals (
                        meal_id serial PRIMARY KEY, 
                        meal varchar(50), 
                        category varchar(20) 
                    );""";

    static final String CREATE_INGREDIENTS = """
                    CREATE TABLE IF NOT EXISTS ingredients (
                        ingredient_id serial PRIMARY KEY, 
                        ingredient varchar(50), 
                        meal_id integer 
                    );""";

    static final String SELECT_MEALS = "SELECT * FROM meals;";

    static final String SELECT_INGREDIENTS = "SELECT * FROM ingredients;";

    static final String INSERT_MEALS = "SELECT * FROM ingredients;";

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
            st.executeQuery(CREATE_MEALS);
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
            st.executeQuery(CREATE_INGREDIENTS);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            Database.closeStatement(st);
        }
    }

    public HashSet<Meal> getAllMeals() {
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

    private HashSet<Ingredient> getAllIngredients() {
        HashSet<Ingredient> ingredients = new HashSet<>();
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

    public void insertIntoMeals(Meal meal) {

    }
}
