package mealplanner.utils;

import mealplanner.model.enums.Category;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
    public static boolean isValidCategoryName(String categoryName) {
        if (!Category.isValidName(categoryName)) {
            StringBuilder errorMsg = new StringBuilder("Wrong meal category! Choose from:");
            for (Category category: Category.values()) {
                errorMsg.append(" ").append(category.getValue()).append(",");
            }
            errorMsg.deleteCharAt(errorMsg.length() - 1);
            errorMsg.append(".");
            System.out.println(errorMsg);
            return false;
        }
        return true;
    }

    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile("[a-zA-Z]+[a-zA-Z ]*[a-zA-Z]+");
        Matcher matcher = pattern.matcher(name);
        if (!matcher.matches()) {
            System.out.println("Wrong format. Use letters only!");
            return false;
        }
        return true;
    }

    public static boolean areAllValidNames(String[] names) {
        for (String name: names) {
            if (!isValidName(name.trim())) {
                return false;
            }
        }
        return true;
    }
}
