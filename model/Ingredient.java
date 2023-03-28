package mealplanner.model;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private Integer id;
    private String name;

    private Integer mealId;

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(Integer id, String name, Integer mealId) {
        this.id = id;
        this.name = name;
        this.mealId = mealId;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMealId() {
        return mealId;
    }

    public void setMealId(Integer mealId) {
        this.mealId = mealId;
    }
}
