import controller.ExpenseTrackerController;
import java.util.List;
import javax.swing.JOptionPane;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;

public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    ExpenseTrackerView view = new ExpenseTrackerView();
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);

    // Initialize view
    view.setVisible(true);

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      List<String> errorStrings = controller.addTransaction(amount, category);
      
      if (!errorStrings.isEmpty()) {
        String msg = "Please fix the following:\n- " + String.join("\n- ", errorStrings);
        JOptionPane.showMessageDialog(view, msg, "Invalid input", JOptionPane.ERROR_MESSAGE);
        view.toFront();
      }
    });

    // Filter button handler
    view.getApplyFilterBtn().addActionListener(e -> {
      String filterType = view.getSelectedFilterType();
      String filterValue = view.getFilterValue();

      List<String> errors = controller.applyFilter(filterType, filterValue);

      if (!errors.isEmpty()) {
        String msg = "Please fix the following:\n- " + String.join("\n- ", errors);
        JOptionPane.showMessageDialog(view, msg, "Invalid filter", JOptionPane.ERROR_MESSAGE);
        view.toFront();
      }
    });

  }

}