package mealplanner.model;

import mealplanner.model.enums.Category;

import java.io.Serializable;
import java.util.List;

public class Meal implements Serializable {

    private Integer id;
    private String name;

    private Category category;
    private List<Ingredient> ingredients;

    public Meal(String name, Category category, List<Ingredient> ingredients) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
    }

    public Meal(String name, Category category, List<Ingredient> ingredients, Integer id) {
        this(name, category, ingredients);
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
