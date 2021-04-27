package window;

import controller.LoginController;
import controller.MainController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainWindow {
    @FXML
    public Label agentLogged;
    MainController controller;

    public void setController(MainController controller){
        this.controller = controller;
    }
    public void setAgent(String agentsName)
    {
        agentLogged.setText(agentsName);
    }
}
