package mealplanner.model;

import java.util.List;

public class Meal {
    private String name;
    private List<Ingredient> ingredients;

    public Meal(String name, List<Ingredient> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
