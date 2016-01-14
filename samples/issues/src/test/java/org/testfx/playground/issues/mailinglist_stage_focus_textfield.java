package org.testfx.playground.issues;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.service.query.NodeQuery;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.GeneralMatchers.typeSafeMatcher;
import static org.testfx.matcher.base.NodeMatchers.hasText;

public class mailinglist_stage_focus_textfield extends FxRobot {

    @Before
    public void setup() throws Exception {
        FxToolkit.registerPrimaryStage();

        FxToolkit.setupFixture(() -> {
            Stage stage1 = new Stage();
            Stage stage2 = new Stage();

            initStage(stage1, 100, 100, "1st stage");
            initStage(stage2, 300, 100, "2nd stage");

            stage1.setScene(new Scene(loadFxmlParent("stage-focus-textfield.fxml")));
            stage2.setScene(new Scene(loadFxmlParent("stage-focus-textfield.fxml")));
            stage1.show();
            stage2.show();
        });
    }

    private void initStage(Stage stage, double x, double y, String title) {
        stage.setX(x);
        stage.setY(y);
        stage.setTitle(title);
    }

    @Test
    public void test_foo() {
        // given:
        interact(() -> window("2nd stage").requestFocus());

        // when:
        write("Alfred E.").push(KeyCode.TAB).write("Neuman");

        // then:
        verifyThat(from("2nd stage").lookup("#fooText"), hasText("Alfred E."));
        verifyThat(from("2nd stage").lookup("#barText"), hasText("Neuman"));

        verifyThat(window("2nd stage"), isWindowFocused());
        verifyThat(from("2nd stage").lookup("#barText"), isFocused());
    }

    private Window window(String stageTitleRegex) {
        return robotContext().getWindowFinder().window(stageTitleRegex);
    }

    private NodeQuery from(String stageTitleRegex) {
        return from(rootNodeOf(stageTitleRegex));
    }

    private Node rootNodeOf(String stageTitleRegex) {
        return rootNode((Window) window(stageTitleRegex));
    }

    public static Matcher<Window> isWindowFocused() {
        String descriptionText = "";
        return typeSafeMatcher(Window.class, descriptionText, window -> window.isFocused());
    }

    public static Matcher<Window> isFullScreen() {
        String descriptionText = "";
        return typeSafeMatcher(Stage.class, descriptionText, stage -> stage.isFullScreen());
    }

    public static Matcher<Node> isFocused() {
        String descriptionText = "";
        return typeSafeMatcher(Node.class, descriptionText, node -> node.isFocused());
    }

    private Parent loadFxmlParent(String resource) {
        URL resourceUrl = getClass().getResource(resource);
        if (resourceUrl == null) {
            throw new RuntimeException("FXML file '" + resource + "' not found.");
        }
        try {
            return (Parent) FXMLLoader.load(resourceUrl);
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

}
