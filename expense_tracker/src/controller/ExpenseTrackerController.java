package controller;

import filter.AmountFilter;
import filter.CategoryFilter;
import filter.TransactionFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public List<String> addTransaction(double amount, String category) {
    List<String> validatedData = InputValidation.validateAmountAndCategory(amount, category);
      if (!validatedData.isEmpty()) {
        return validatedData;
      }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return Collections.emptyList();
  }
  
  /**
 * Applies a filter to the transactions currently in the model and updates the view.
 *
 * @param filterType  "none", "amount", or "category"
 * @param filterValue user input for the filter (may be null/empty for "none")
 * @return list of validation error messages; empty if filter was applied successfully
 */
  public List<String> applyFilter(String filterType, String filterValue) {
    List<Transaction> allTransactions = model.getTransactions();

    if (filterType == null || filterType.equalsIgnoreCase("none")) {
      view.refreshTable(allTransactions);
      return Collections.emptyList();
    }

    TransactionFilter strategy = null;

    if (filterType.equalsIgnoreCase("amount")) {
      double amountToFilter;
      try {
        amountToFilter = Double.parseDouble(filterValue);
      } catch (NumberFormatException e) {
        return Arrays.asList("Amount must be a valid number.");
      }

      if (!InputValidation.isValidAmount(amountToFilter)) {
        return Arrays.asList("Amount must be a number greater than 0 and less than 1000.");
      }

      strategy = new AmountFilter(amountToFilter);

    } else if (filterType.equalsIgnoreCase("category")) {
      if (filterValue == null || filterValue.trim().isEmpty()) {
        return Arrays.asList("Category cannot be empty.");
      }

      if (!InputValidation.isValidCategory(filterValue)) {
        return Arrays.asList("Allowed categories are: food, travel, bills, entertainment, other.");
      }

      strategy = new CategoryFilter(filterValue.trim().toLowerCase());
    }

    if (strategy == null) {
      view.refreshTable(allTransactions);
      return Collections.emptyList();
    }

    List<Transaction> filtered = strategy.filter(allTransactions);
    view.refreshTable(filtered);

    return Collections.emptyList();
  }
}