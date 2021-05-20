import repository.DBOrderRepository;
import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Agent;
import model.Order;
import model.Product;
import repository.*;
import service.Service;
import validators.AgentValidator;
import validators.IValidator;
import validators.OrderValidator;
import validators.ProductValidator;
import window.LoginWindow;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {

    //initiate repositories
    IAgentRepository agentRepository;
    IProductRepository productRepository;
    IOrderRepository orderRepository;
    //initiate validators;
    IValidator<Agent> agentIValidator;
    IValidator<Product> productIValidator;
    IValidator<Order> orderIValidator;
    //initiate service;
    Service service;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Properties props = new Properties();
        try{
            props.load(new FileReader("bd.properties"));
        }catch (IOException e){
            System.out.println("Cannot find bd.properties " + e);
        }

        //repositories
        agentRepository = new ORMAgentRepository();
        productRepository = new ORMProductRepository();
//        orderRepository = new ORMOrderRepository();
        orderRepository = new DBOrderRepository(props);
        //validators
        agentIValidator = new AgentValidator();
        productIValidator = new ProductValidator();
        orderIValidator = new OrderValidator();
        //service
        service = new Service(agentRepository, productRepository, orderRepository, agentIValidator, productIValidator, orderIValidator);
        initView(stage);
    }

    private void initView(Stage primaryStage) throws Exception{
        LoginController loginController = new LoginController();
        loginController.setService(service);
        loginController.initiateLoginProcedure(primaryStage);
    }
}
