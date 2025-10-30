package filter;

import java.util.List;
import model.Transaction;

/**
 * Strategy interface for filtering transactions.
 * Returns a subset of the given list.
 */
public interface TransactionFilter {
    List<Transaction> filter(List<Transaction> transactions);
}
