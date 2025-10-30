package filter;

import java.util.ArrayList;
import java.util.List;
import model.Transaction;

public class AmountFilter implements TransactionFilter {

    private final double amount;

    public AmountFilter(double amount) {
        this.amount = amount;
    }

    @Override
    public List<Transaction> filter(List<Transaction> transactions) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            // exact match; could be changed to >= / <= if you like
            if (Double.compare(t.getAmount(), amount) == 0) {
                result.add(t);
            }
        }
        return result;
    }
}