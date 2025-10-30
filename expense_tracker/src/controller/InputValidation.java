package controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class InputValidation {

    private static final Set<String> ALLOWED_CATEGORIES = Set.of("food", "travel", "bills", "entertainment", "other");

    private InputValidation() {}

    // Validates the amount field
    public static boolean isValidAmount(double amount) {
        return amount > 0 && amount < 1000;
    }

    // Validates the category field
    public static boolean isValidCategory(String categoryString) {
        String category = categoryString == null ? null : categoryString.trim().toLowerCase();
        return category != null && category.matches("[a-zA-Z]+") && ALLOWED_CATEGORIES.contains(category);
    }

    // Validation method to check if both the fields passes the condition
    public static List<String> validateAmountAndCategory(double amount, String category) {
        List<String> errors = new ArrayList<>();
        if (!isValidAmount(amount)) {
            errors.add("Amount must be a number greater than 0 and less than 1000.");
        }
        if (!isValidCategory(category)) {
            errors.add("Allowed categories are: food, travel, bills, entertainment, other.");
        }
        return errors;
    }
}