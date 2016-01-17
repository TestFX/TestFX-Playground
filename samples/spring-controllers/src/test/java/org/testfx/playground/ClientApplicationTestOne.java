package org.testfx.playground;

import org.junit.Assert;
import org.junit.Test;
import org.testfx.api.FxRobotException;

public class ClientApplicationTestOne extends ClientApplicationTestBase {

	@Test(expected = FxRobotException.class)
	public void clickNonexistentElement() {
		clickOn("Nonexistent Element");
	}

	@Test
	public void selectPlayer() {
		clickOn("Cleveland Cavaliers");
		clickOn("LeBron James");

		Assert.assertEquals("LeBron James", playerTable.getSelectionModel().getSelectedItem().getName());
	}

}
