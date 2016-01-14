package org.testfx.playground.basics.counter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.service.query.impl.NodeQueryUtils.hasText;

public class CounterTest extends ApplicationTest {

    private static class CounterPane extends StackPane {
        public CounterPane() {
            // create countButton.
            Button countButton = new Button("count");
            countButton.setId("countButton");

            // create countValue.
            TextField countValue = new TextField("0");
            countValue.setId("countValue");

            // initialize controls.
            countButton.setOnAction(event -> {
                int value = asInteger(countValue.getText());
                int incrementedValue = value + 1;
                countValue.setText(asString(incrementedValue));
            });
            countValue.setEditable(false);
            countValue.setPrefWidth(50);

            // create and add containers.
            HBox box = new HBox(10, countButton, countValue);
            box.setPadding(new Insets(10));
            box.setAlignment(Pos.CENTER);
            getChildren().add(box);
        }
    }

    private static int asInteger(String value) { return Integer.parseInt(value); }
    private static String asString(int value) { return value + ""; }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new CounterPane(), 200, 50));
        stage.show();
    }

    @Test
    public void should_initialize_countValue() {
        // expect:
        verifyThat("#countValue", hasText("0"));
    }

    @Test
    public void should_increment_countValue() {
        // when:
        clickOn("count");
        
        // then:
        verifyThat("#countValue", hasText("1"));
    }

    @Test
    public void should_increment_countValue_again() {
        // when:
        clickOn("count").clickOn("count");

        // then:
        verifyThat("#countValue", hasText("2"));
    }

}
