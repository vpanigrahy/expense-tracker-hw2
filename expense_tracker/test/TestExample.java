// package test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;


public class TestExample {

    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;

    @Before
    public void setup() {
        model = new ExpenseTrackerModel();
        view = new ExpenseTrackerView();

        view.setVisible(false);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        controller = new ExpenseTrackerController(model, view);
        controller.refresh();
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions();
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }

    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add a transaction
        List<String> errors = controller.addTransaction(50.00, "food");
        assertTrue("Expected no validation errors", errors.isEmpty());
    
        // Post-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Check the contents of the list
        assertEquals(50.00, getTotalCost(), 0.01);
    }

    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());
    
        // Perform the action: Add and remove a transaction
        Transaction addedTransaction = new Transaction(50.00, "Groceries");
        model.addTransaction(addedTransaction);
    
        // Pre-condition: List of transactions contains one transaction
        assertEquals(1, model.getTransactions().size());
    
        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);
    
        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());
    
        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    @Test
    public void testAddValidTransaction_ViewAndTotal() {
        List<String> errors = controller.addTransaction(50.00, "food");
        assertTrue("Valid transaction should have no errors", errors.isEmpty());

        controller.refresh();

        // columns are {serial, Amount, Category, Date}
        int rowCount = view.getTableModel().getRowCount();
        assertEquals("Should have 1 transaction row + 1 total row", 2, rowCount);

        Object amount = view.getTableModel().getValueAt(0, 1);
        Object category = view.getTableModel().getValueAt(0, 2);
        assertEquals(50.00, (double) amount, 0.0001);
        assertEquals("food", category);

        Object total = view.getTableModel().getValueAt(1, 3);
        assertEquals(50.00, (double) total, 0.0001);
    }

    @Test
    public void testInputValidationForInvalidAmount() {
        List<String> errors = controller.addTransaction(-5.0, "food");
        assertFalse("Invalid amount should produce errors", errors.isEmpty());

        controller.refresh();

        // model unchanged
        assertEquals(0, model.getTransactions().size());

        // test that table is unchanged
        int rowCount = view.getTableModel().getRowCount();
        assertEquals("Still only total row", 1, rowCount);

        Object total = view.getTableModel().getValueAt(0, 3);
        assertEquals(0.0, (double) total, 0.0001);
    }

    @Test
    public void testInputValidationForInvalidCategory() {
        List<String> errors = controller.addTransaction(30.0, "snacks");
        assertFalse("Invalid category should produce errors", errors.isEmpty());

        controller.refresh();

        assertEquals(0, model.getTransactions().size());
        int rowCount = view.getTableModel().getRowCount();
        assertEquals("Should only have total row", 1, rowCount);

        Object total = view.getTableModel().getValueAt(0, 3);
        assertEquals(0.0, (double) total, 0.0001);
    }

    @Test
    public void testFilterByAmount() {
        assertTrue(controller.addTransaction(10.0, "food").isEmpty());
        assertTrue(controller.addTransaction(20.0, "travel").isEmpty());
        assertTrue(controller.addTransaction(10.0, "bills").isEmpty());

        // apply amount filter
        List<String> errors = controller.applyFilter("amount", "10");
        assertTrue("Filter by amount should succeed", errors.isEmpty());

        int rowCount = view.getTableModel().getRowCount();
        assertEquals("2 transactions with amount=10 + total", 3, rowCount);

        Object amt0 = view.getTableModel().getValueAt(0, 1);
        Object amt1 = view.getTableModel().getValueAt(1, 1);
        assertEquals(10.0, (double) amt0, 0.0001);
        assertEquals(10.0, (double) amt1, 0.0001);

        Object total = view.getTableModel().getValueAt(2, 3);
        assertEquals(20.0, (double) total, 0.0001);
    }

    @Test
    public void testFilterByCategory() {
        assertTrue(controller.addTransaction(15.0, "food").isEmpty());
        assertTrue(controller.addTransaction(25.0, "travel").isEmpty());
        assertTrue(controller.addTransaction(35.0, "food").isEmpty());

        List<String> errors = controller.applyFilter("category", "food");
        assertTrue("Filter by category should succeed", errors.isEmpty());

        int rowCount = view.getTableModel().getRowCount();
        assertEquals("2 food rows + total", 3, rowCount);

        Object cat0 = view.getTableModel().getValueAt(0, 2);
        Object cat1 = view.getTableModel().getValueAt(1, 2);
        assertEquals("food", cat0);
        assertEquals("food", cat1);

        Object total = view.getTableModel().getValueAt(2, 3);
        assertEquals(50.0, (double) total, 0.0001);
    } 
}