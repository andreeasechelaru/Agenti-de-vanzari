package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Agent;
import service.Service;
import sun.security.validator.ValidatorException;
import validators.ValidationException;
import window.LoginWindow;
import window.MainWindow;

import java.io.IOException;

public class LoginController {

    Service service;
    LoginWindow window;
    Stage currentStage;

    public LoginController() {
    }

    public void setService(Service service){
        this.service = service;
    }
    public void setCurrentStage(Stage stage){this.currentStage = currentStage;}

    /**
     * Function that open the login window and initiate the program
     * @param primaryStage Stage
     * @throws IOException
     */
    public void initiateLoginProcedure(Stage primaryStage) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/views/loginView.fxml"));
        AnchorPane loginLayout = Loader.load();
        window = Loader.getController();
        window.setController(this);
        Scene loginScene = new Scene(loginLayout);
        primaryStage.setScene(loginScene);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        setCurrentStage(primaryStage);
        primaryStage.show();
        primaryStage.setWidth(617);
        primaryStage.setHeight(436);
        primaryStage.setTitle("Login Window");

    }

    /**
     * Function that handle the Login Button clicked and if the agent logged is valid it initiates the main procedure
     * @param username String
     * @param password String
     * @throws ValidationException
     * @throws IOException
     */
    public void handleLogin(String username, String password) throws ValidationException, IOException {
        Agent agent = new Agent("name", username, password);
        agent = service.login(agent);

        //open new window
        MainController mainController = new MainController();
        mainController.setService(service);
        mainController.setLoggedAgent(agent);
        mainController.initiateMainProcedure();

    }




}
