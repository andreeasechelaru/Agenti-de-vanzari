package window;

import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sun.security.validator.ValidatorException;
import validators.ValidationException;

import java.io.IOException;

public class LoginWindow {

    LoginController controller;

    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    Scene currentScene;

    public void setController(LoginController controller){
        this.controller = controller;
    }

    public String typeUsername(){
        return usernameField.getText();
    }

    public String typePassword(){
        return passwordField.getText();
    }

    public void clickLoginButton(ActionEvent actionEvent) {
        try{
            controller.handleLogin(typeUsername(), typePassword());
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        }catch (ValidationException | IOException e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("User invalid!");
            errorAlert.setContentText(e.getMessage());
            errorAlert.setHeight(300);
            errorAlert.showAndWait();
        }
    }

}

