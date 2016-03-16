package org.testfx.playground.view;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This component lays out a TableView and a TextField (used to filter the
 * TableView).
 * 
 * <h4>Usage</h4>
 * <ol>
 * <li>Create a FilteredTable</li>
 * <li>Set its backing data</li>
 * <li>Set its filter function</li>
 * </ol>
 * 
 * <h5>Note on setting data</h5>
 * 
 * Use {@link #setItems(ObservableList) FilteredTable.setItems}, not
 * {@link TableView#setItems(ObservableList) TableView.setItems}, to populate
 * the table.
 * 
 * <h4>Java Example</h4>
 * 
 * <pre>
 * <code>
 * TableView{@literal <T>} tableView = new TableView<>();
 * TextField textField = new TextField();
 * ObservableList{@literal <T>} items = FXCollections.observableArrayList();
 * ...
 * FilteredTable{@literal <T>} filteredTable = new FilteredTable<>(tableView, textField);
 * filteredTable.setItems(items);
 * filteredTable.setFilterFunction((item, filterText) {@literal ->} item.toString().contains(filterText));
 * </code>
 * </pre>
 * 
 * <h4>FXML Example</h4>
 * 
 * <h5>Controller.fxml</h5>
 * 
 * <pre>
 * {@code
 * <FilteredTable fx:id="filteredTable">
 *     <table>
 *         <TableView />
 *     </table>
 *     <filter>
 *         <TextField />
 *     </filter>
 * </FilteredTable>
 * }
 * </pre>
 * 
 * <h5>Controller.java</h5>
 * 
 * <pre>
 * <code>
 * {@literal @}FXML private FilteredTable filteredTable;
 * 
 * public void initialize() {
 *     filteredTable.setItems(items);
 *     filteredTable.setFilterFunction(item, filterText) -> item.toString().contains(filterText));
 * }
 * <code>
 * </pre>
 *
 * @param <T>
 *            The type of items contained in the table
 */
public class FilteredTable<T> extends VBox {

	private final TableView<T> tableView;
	private final TextField textField;

	private BiFunction<T, String, Boolean> filterFunction;
	private FilteredList<T> filteredItems;

	/**
	 * The usage is explained at the class level.
	 */
	public FilteredTable(@NamedArg("table") TableView<T> tableView, @NamedArg("filter") TextField textField) {

		this.tableView = tableView;
		this.textField = textField;

		super.getChildren().setAll(tableView, textField);
		super.setVgrow(tableView, Priority.ALWAYS);

		// bindings
		textField.prefWidthProperty().bind(tableView.widthProperty());
		textField.textProperty().addListener((observable, oldFilter, newFilter) -> {
			filteredItems.setPredicate(createPredicate(newFilter));
		});
	}

	private Predicate<T> createPredicate(String filterText) {
		return item -> filterText == null || filterFunction == null || filterFunction.apply(item, filterText);
	}

	/**
	 * The filter function takes two arguments &mdash; a table item and the
	 * filter text &mdash; and returns true if the item should be shown in the
	 * table.
	 * 
	 * @param filterFunction
	 *            The filter function to set
	 */
	public void setFilterFunction(BiFunction<T, String, Boolean> filterFunction) {
		this.filterFunction = filterFunction;
	}

	/**
	 * Set the backing data for the TableView. This should be used instead of
	 * calling {@link TableView#setItems(ObservableList) setItems} on the
	 * wrapped TableView.
	 * 
	 * @param items
	 *            The items to set
	 */
	public void setItems(ObservableList<T> items) {
		Objects.requireNonNull(items, "items");
		if (filterFunction == null) {
			filteredItems = items.filtered(item -> true);
		} else {
			filteredItems = items.filtered(createPredicate(textField.getText()));
		}
		final SortedList<T> sortedItems = filteredItems.sorted();
		sortedItems.comparatorProperty().bind(tableView.comparatorProperty());
		tableView.setItems(sortedItems);
	}

}
