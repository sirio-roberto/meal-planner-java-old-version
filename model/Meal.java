package mealplanner.model;

import mealplanner.model.enums.Category;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Meal implements Serializable {

    private Integer id;
    private String name;

    private Category category;
    private List<Ingredient> ingredients;

    public Meal(String name, Category category, List<Ingredient> ingredients) {
        this.name = name;
        this.category = category;
        this.ingredients = ingredients;
        this.id = getRandomId();
    }

    public Meal(Integer id, String name, Category category, List<Ingredient> ingredients) {
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

    private Integer getRandomId() {
        Random random = new Random();
        return random.nextInt(1, 1000);
    }
}
