package org.testfx.playground;

import javafx.scene.control.TableView;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.playground.model.Player;

public abstract class ClientApplicationTestBase extends ApplicationTest {

	protected TableView<Player> playerTable;

	@BeforeClass
	public static void setUpClass() throws Exception {
		ApplicationTest.launch(ClientApplication.class);
	}

	@Before
	public void setUp() {
		playerTable = lookup("#playerTable").queryFirst();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.show();
	}

}
