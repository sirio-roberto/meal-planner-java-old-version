package mealplanner.model.enums;

public enum Category {
    BREAKFAST("breakfast", 0),
    LUNCH("lunch", 1),
    DINNER("dinner", 2);

    private final String value;

    private Integer priority;

    Category(String value, Integer priority) {
        this.value = value;
        this.priority = priority;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValidName(String name) {
        for (Category category: values()) {
            if (category.value.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static Category getCategoryByValue(String value) {
        for (Category category: values()) {
            if (category.value.equals(value)) {
                return category;
            }
        }
        return null;
    }

    public Integer getPriority() {
        return priority;
    }
}
