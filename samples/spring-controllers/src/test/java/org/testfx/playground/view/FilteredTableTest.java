package org.testfx.playground.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.playground.view.FilteredTable;

public class FilteredTableTest extends ApplicationTest {

	private static class Item {
		public final StringProperty name;
		public Item(String name) {
			this.name = new SimpleStringProperty(name);
		}
		public String getName() {
			return name.get();
		}
	}

	@Override
	public void start(Stage stage) throws Exception {

		TableView<Item> tableView = new TableView<>();
		TableColumn<Item, String> tableColumn = new TableColumn<>("Name");
		tableColumn.setCellValueFactory(cellData -> cellData.getValue().name);
		tableView.getColumns().add(tableColumn);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		FilteredTable<Item> filterableTable = new FilteredTable<>(tableView, new TextField());
		filterableTable.setItems(toItems("foo", "bar", "baz", "Testing", "Testing, testing, 1, 2, 3"));
		filterableTable.setFilterFunction((team, filterText) -> team.getName().contains(filterText));

		stage.setScene(new Scene(filterableTable, 600, 400));
		stage.show();

	}

	public ObservableList<Item> toItems(String... names) {
		ObservableList<Item> items = FXCollections.observableArrayList();
		for (String name : names) {
			items.add(new Item(name));
		}
		return items;
	}

	@Test
	public void testFilter() {

		TableView<Item> tableView = lookup(".table-view").queryFirst();
		TextField textField = lookup(".text-field").queryFirst();

		clickOn(textField);

		// add filter
		write("Testing");
		FxAssert.verifyThat(tableView, node -> node.getItems().size() == 2);

		// remove filter
		push(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
		push(KeyCode.BACK_SPACE);
		FxAssert.verifyThat(tableView, node -> node.getItems().size() == 5);

		// add filter
		write("1, 2, 3");
		FxAssert.verifyThat(tableView, node -> node.getItems().size() == 1);

	}

}
