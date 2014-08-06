package org.testfx.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import org.junit.Test;
import org.loadui.testfx.Assertions;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.controls.Commons;

public class DemoGuiTest extends GuiTest {
	
	@Override
	protected Parent getRootNode() {
		final Label label = new Label("not logged in");
		label.setId("loginStatus");
		
		final Button button = new Button("login");
		button.setId("loginButton");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				label.setText("logged in");
			}
		});
		return new VBox(label, button);
	}
	
	@Test
	public void should_click_login_button() {
		// when:
		click("#loginButton");
		
		// then:
		Assertions.verifyThat("#loginStatus", Commons.hasText("logged in"));
	}

}
