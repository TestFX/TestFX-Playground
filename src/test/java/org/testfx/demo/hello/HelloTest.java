package org.testfx.demo.hello;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Test;
import org.loadui.testfx.framework.robot.impl.FxRobotImpl;
import org.testfx.api.FxLifecycle;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

public class HelloTest extends FxRobotImpl {

    public static class HelloApplication extends Application {
        public void start(Stage stage) throws Exception {
            Label messageLabel = new Label();
            messageLabel.setId("messageLabel");

            Button clickButton = new Button("Click me!");
            clickButton.setId("clickButton");
            clickButton.setOnAction((actionEvent) -> {
                messageLabel.setText("Hello!");
            });

            VBox box = new VBox(5, messageLabel, clickButton);
            box.setAlignment(Pos.CENTER);
            stage.setScene(new Scene(box, 200, 200));
            stage.show();
        }
    }

    @Before
    public void setup() throws Exception {
        FxLifecycle.registerPrimaryStage();
        FxLifecycle.setupApplication(HelloApplication.class);
    }

    @Test
    public void should_click_login_button() {
        // when:
        clickOn("#clickButton");
        
        // then:
        verifyThat("#messageLabel", hasText("Hello!"));
    }

}
