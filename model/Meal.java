package mealplanner.model;

import mealplanner.model.enums.Category;

import java.util.List;

public class Meal {
    private String name;

    private Category category;
    private List<Ingredient> ingredients;

    public Meal(String name, Category category, List<Ingredient> ingredients) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Category getCategory() {
        return category;
    }
}
