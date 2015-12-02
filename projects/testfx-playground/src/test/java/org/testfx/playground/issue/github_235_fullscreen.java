package org.testfx.playground.issue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import static org.hamcrest.Matchers.equalTo;
import static org.testfx.api.FxAssert.verifyThat;

public class github_235_fullscreen {
    Stage stage;

    @Before
    public void setup() throws Exception {
        stage = FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(FullScreenApp.class);
    }

    public static class FullScreenApp extends Application {
        @Override
        public void start(Stage stage) throws Exception {
            Label label = new Label("content");
            stage.setScene(new Scene(new StackPane(label), 100, 100));
            stage.setFullScreen(true);
            stage.show();
        }
    }

    @Test
    public void stage_should_be_visible() {
        // expect:
        verifyThat(stage.isFullScreen(), equalTo(true));
    }
}
