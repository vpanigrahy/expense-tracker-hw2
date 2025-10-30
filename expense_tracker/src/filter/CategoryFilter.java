package filter;

import java.util.ArrayList;
import java.util.List;
import model.Transaction;

/**
 * Class for filtering transactions based on category.
 * Implementations return a subset of the given list.
 */
public class CategoryFilter implements TransactionFilter {

    private final String category; // already trimmed + lowercased

    public CategoryFilter(String category) {
        this.category = category;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getCategory() != null && t.getCategory().trim().equalsIgnoreCase(category)) {
                result.add(t);
            }
        }
        return result;
    }
}