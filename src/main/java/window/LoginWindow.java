package window;

import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Product;
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

    /**
     * Returns the username inserted by the user
     * @return username String
     */
    public String typeUsername(){
        return usernameField.getText();
    }

    /**
     * Returns the password inserted by the user
     * @return password String
     */
    public String typePassword(){
        return passwordField.getText();
    }

    /**
     * Handle the user's click on login button. If the agent is valid the window is closed in order to open a new one
     * corresponding to main stage, else an alert window will show up with the error messages.
     * @param actionEvent
     */
    public void clickLoginButton(ActionEvent actionEvent) {
        try{
            controller.handleLogin(typeUsername(), typePassword());
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        }catch (ValidationException | IOException e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Invalid agent!");
            errorAlert.setContentText(e.getMessage());
            errorAlert.setHeight(300);
            errorAlert.showAndWait();
        }
    }


}

