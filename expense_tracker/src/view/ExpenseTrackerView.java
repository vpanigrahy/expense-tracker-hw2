package view;

import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Transaction;

public class ExpenseTrackerView extends JFrame {

  private JTable transactionsTable;
  private JButton addTransactionBtn;
  private JFormattedTextField amountField;
  private JTextField categoryField;
  private DefaultTableModel model;

  private JComboBox<String> filterTypeCombo;
  private JTextField filterValueField;
  private JButton applyFilterBtn;
  
  public ExpenseTrackerView() {
    setTitle("Expense Tracker"); // Set title
    setSize(600, 400); // Make GUI larger

    String[] columnNames = {"serial", "Amount", "Category", "Date"};
    this.model = new DefaultTableModel(columnNames, 0);

    addTransactionBtn = new JButton("Add Transaction");

    // Create UI components
    JLabel amountLabel = new JLabel("Amount:");
    NumberFormat format = NumberFormat.getNumberInstance();

    amountField = new JFormattedTextField(format);
    amountField.setColumns(10);

    
    JLabel categoryLabel = new JLabel("Category:");
    categoryField = new JTextField(10);

    // Create table
    transactionsTable = new JTable(model);

    filterTypeCombo = new JComboBox<>(new String[] {
      "No filter",        // index 0
      "By amount",        // index 1
      "By category"       // index 2
    });
    filterValueField = new JTextField(10);
    applyFilterBtn = new JButton("Apply filter");
  
    // Layout components
    JPanel inputPanel = new JPanel();
    inputPanel.add(amountLabel);
    inputPanel.add(amountField);
    inputPanel.add(categoryLabel); 
    inputPanel.add(categoryField);
    inputPanel.add(addTransactionBtn);

    JPanel filterPanel = new JPanel();
    filterPanel.add(new JLabel("Filter:"));
    filterPanel.add(filterTypeCombo);
    filterPanel.add(new JLabel("Value:"));
    filterPanel.add(filterValueField);
    filterPanel.add(applyFilterBtn);

    JPanel northPanel = new JPanel();
    northPanel.setLayout(new BorderLayout());
    northPanel.add(inputPanel, BorderLayout.NORTH);
    northPanel.add(filterPanel, BorderLayout.SOUTH);
  
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addTransactionBtn);
  
    // Add panels to frame
    add(northPanel, BorderLayout.NORTH);
    add(new JScrollPane(transactionsTable), BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);

    // Set frame properties
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public void refreshTable(List<Transaction> transactions) {
      // Clear existing rows
      model.setRowCount(0);
      // Get row count
      int rowNum = model.getRowCount();
      double totalCost=0;
      // Calculate total cost
      for(Transaction t : transactions) {
        totalCost+=t.getAmount();
      }
      // Add rows from transactions list
      for(Transaction t : transactions) {
        model.addRow(new Object[]{rowNum+=1,t.getAmount(), t.getCategory(), t.getTimestamp()}); 
      }
        // Add total row
        Object[] totalRow = {"Total", null, null, totalCost};
        model.addRow(totalRow);
  
      // Fire table update
      transactionsTable.updateUI();
  }  
  
  public JButton getAddTransactionBtn() {
    return addTransactionBtn;
  }
  public DefaultTableModel getTableModel() {
    return model;
  }
  // Other view methods
    public JTable getTransactionsTable() {
    return transactionsTable;
  }

  public double getAmountField() {
    if(amountField.getText().isEmpty()) {
      return 0;
    }else {
    double amount = Double.parseDouble(amountField.getText());
    return amount;
    }
  }

  public void setAmountField(JFormattedTextField amountField) {
    this.amountField = amountField;
  }

  public String getCategoryField() {
    return categoryField.getText();
  }

  public void setCategoryField(JTextField categoryField) {
    this.categoryField = categoryField;
  }

  /* Method to get applied filter button */
  public JButton getApplyFilterBtn() {
    return applyFilterBtn;
  }

  /**
 * @return the filter type selected in the UI:
 *         "none", "amount", or "category".
 */
  public String getSelectedFilterType() {
    String selected = (String) filterTypeCombo.getSelectedItem();
    if ("By amount".equals(selected)) {
      return "amount";
    } else if ("By category".equals(selected)) {
      return "category";
    }
    return "none";
  }

  /**
   *  To get the value used in filter
   * @return the text value used to filter
   * **/
  public String getFilterValue() {
    return filterValueField.getText();
  }
}
