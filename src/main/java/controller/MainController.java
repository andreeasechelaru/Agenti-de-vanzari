package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Agent;
import service.Service;
import window.LoginWindow;
import window.MainWindow;

import java.io.IOException;

public class MainController {

    Service service;
    Agent loggedAgent;

    public void setService(Service service){
        this.service = service;
    }
    public void setLoggedAgent(Agent agent){this.loggedAgent = agent;}

    public void initiateMainProcedure() throws IOException {

        Stage stage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/views/mainView.fxml"));
        BorderPane mainLayout = Loader.load();
        MainWindow window = Loader.getController();
        window.setController(this);
        window.setAgent(loggedAgent.getName());
        Scene mainScene = new Scene(mainLayout);
        stage.setScene(mainScene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        stage.show();
        stage.setWidth(830);
        stage.setHeight(560);
        stage.setTitle("Main Window for " + loggedAgent.getName());

    }

}
