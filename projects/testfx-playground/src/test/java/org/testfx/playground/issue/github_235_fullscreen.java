package org.testfx.playground.issue;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.hamcrest.Matchers.equalTo;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasText;

public class github_235_fullscreen extends ApplicationTest {
    Stage stageFixture;

    @Override
    public void start(Stage stage) throws Exception {
        Button button = new Button("click me!");
        button.setOnAction(actionEvent -> button.setText("clicked!"));

        stageFixture = stage;
        stage.setScene(new Scene(new StackPane(button), 100, 100));
        stage.setFullScreen(true);
        stage.show();
    }

    @Test
    public void stage_should_be_visible() {
        // expect:
        verifyThat(stageFixture.isFullScreen(), equalTo(true));
    }

    @Test
    public void should_click_on_button() {
        // when:
        clickOn(".button");

        // then:
        verifyThat(".button", hasText("clicked!"));
    }
}
