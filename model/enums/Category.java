package mealplanner.model.enums;

public enum Category {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner");

    private String value;

    Category(String value) {
        this.value = value;
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
}
