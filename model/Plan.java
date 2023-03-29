package mealplanner.model;

import mealplanner.model.enums.Category;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Plan implements Serializable {

    private Integer id;

    private String weekDay;
    private Meal meal;

    public Plan(Meal meal, String weekDay) {
        this.meal = meal;
        this.weekDay = weekDay;
    }

    public Plan(Integer id, Meal meal, String weekDay) {
        this(meal, weekDay);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}
