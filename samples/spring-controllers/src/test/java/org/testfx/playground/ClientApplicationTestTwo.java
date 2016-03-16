package org.testfx.playground;

import javafx.scene.control.TableColumn;

import org.junit.Assert;
import org.junit.Test;
import org.testfx.playground.model.Player;

public class ClientApplicationTestTwo extends ClientApplicationTestBase {

	@Test
	public void importTeamsAndPlayers() {
		clickOn("File").moveTo("Import").clickOn("More Teams");
		clickOn("Miami Heat");

		Assert.assertEquals(0, playerTable.getItems().size());

		clickOn("File").moveTo("Import").clickOn("More Players");
		clickOn("Chris Bosh");

		Assert.assertEquals("Chris Bosh", playerTable.getSelectionModel().getSelectedItem().getName());
	}

	@Test
	public void toggleFieldGoalPercentage() {
		// TODO: lookup the TableColumn directly (by id)?
		TableColumn<Player, ?> fieldGoalPercentageColumn = null;
		for (TableColumn<Player, ?> column : playerTable.getColumns()) {
			if (column.getText().equals("FG %")) {
				fieldGoalPercentageColumn = column;
			}
		}

		if (fieldGoalPercentageColumn == null) {
			Assert.fail("Could not find the FG % column");
		}

		Assert.assertTrue(fieldGoalPercentageColumn.isVisible());
		clickOn("View").clickOn("Show FG %");
		Assert.assertFalse(fieldGoalPercentageColumn.isVisible());
		clickOn("View").clickOn("Show FG %");
		Assert.assertTrue(fieldGoalPercentageColumn.isVisible());
	}

}
