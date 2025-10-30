# hw2 – Manual Review

The homework is based on this project named **“Expense Tracker”**, where users will be able to **add / remove / filter** daily transactions.

---

## New functionality (added)

We extended the original app to support **filtering transactions** using the **Strategy design pattern**:

- A new `TransactionFilter` interface was introduced with `List<Transaction> filter(List<Transaction>)`.
- Two concrete strategies were added:
  - `CategoryFilter` – shows only transactions that match a valid category.
  - `AmountFilter` – shows only transactions whose amount matches the given (validated) amount.
- The **controller** (`ExpenseTrackerController`) now exposes `applyFilter(String filterType, String filterValue)` to apply one of:
  - **no filter** (show everything),
  - **filter by amount**,
  - **filter by category**.
- The **view** (`ExpenseTrackerView`) now has a small filter UI:
  - a dropdown: _No filter_ / _By amount_ / _By category_
  - an input field for the filter value
  - an **Apply filter** button
- **Input validation is reused**: the filter input uses the same validation rules as “Add transaction”:
  - amount: number, `0 < amount < 1000`
  - category: one of `food`, `travel`, `bills`, `entertainment`, `other`
- When a filter is applied, **only the matching transactions are displayed** and the **Total row** is recalculated using the filtered subset.

We also added **5 unit tests** in the `test/` (or `src/test/java/`) folder to verify:
1. Add Valid Transaction
2. Input Validation for Amount
3. Input Validation for Category
4. Filter by Amount
5. Filter by Category

These tests live alongside the existing 2 tests and should still pass.

---

## How to build and test (from Terminal)

1. Make sure that you have **Apache Ant** installed. Run:

   ```bash
   ant
   ``` 
   in the root directory (where build.xml is).

2. Generate Javadoc (goes to jdoc/):
   ```bash
   ant document
   ``` 
   Then open jdoc/index.html in a browser.

3. Compile sources:
    ```bash
    ant compile
    ```
Compiled classes will be in the bin/ directory.

4. Run unit tests (this runs the original tests + the 5 new ones):
    ```bash
    ant test
    ```
This will compile and execute all JUnit tests in the test folder.

## How to run (from Terminal)
After building the project (i.e. running `ant`), run:
```java
java ExpenseTrackerApp
```
This will start the Swing GUI.

## Code Modification

You must have a file named `InputValidation.java` to validate the `amount` and `category` fields of this app.

### Validation Rules

* **Amount**
    * Should be greater than 0 and less than 1000.
    * It should be a valid number.
* **Category**
    * Should be a valid string input from the following list:
        `food`, `travel`, `bills`, `entertainment`, `other`

* Invalid input should be rejected and error messages should be displayed in the GUI.
* `ExpenseTrackerApp.java` (and the controller) are updated to use these validations when adding transactions and when applying filters.

## Manual Review

### Examples of satisfying non-functional requirements

* **Understandability**
    * External documentation (this README) makes it easier for users and developers to build and run the app.
    * Javadoc can be generated with `ant document` and browsed from `jdoc/index.html`.

### Examples of violating non-functional requirements (original app)

* **Modularity**
    * The original app did not fully apply MVC.
    * The app should declare the following packages (now done): `model`, `view`, `controller`, and now also a small `filter` package for the strategy classes.

## Understandability

### Brief Explanation of the Project
**Expense Tracker (Swing, MVC-lite + Strategy for filtering)**
A small desktop app to add, view, and now filter daily transactions.

### Features
* Add transactions with amount, category, and timestamp.
* Table view with auto-calculated **Total** row at the bottom.
* **Input validation** (`InputValidation.java`)
    * `amount`: numeric, `0 < amount < 1000`
    * `category`: one of `food`, `travel`, `bills`, `entertainment`, `other`
* **Filtering** (new):
    * Filter by amount (only show matching amounts)
    * Filter by category (only show that category)
    * Or select "No filter" to show all
    * Implemented using the **Strategy design pattern** (`TransactionFilter`, `AmountFilter`, `CategoryFilter`)
    * Uses the same validation as the add form
* Incremental updates and clean error dialogs for invalid input.
* **Javadoc**: API documentation generated to the `jdoc/` folder.



