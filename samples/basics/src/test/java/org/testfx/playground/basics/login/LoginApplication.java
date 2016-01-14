package org.testfx.playground.basics.login;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginApplication extends Application {

    public static void main(String[] args) {
        launch(LoginApplication.class, args);
    }

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label messageLabel;
    @FXML
    Button loginButton;
    @FXML
    Button logoutButton;

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = getClass().getResource("loginForm.fxml");
        Parent parent = FXMLLoader.<Parent>load(resource);
        stage.setTitle("Login Form");
        stage.setScene(new Scene(parent));
        stage.show();
    }

}
