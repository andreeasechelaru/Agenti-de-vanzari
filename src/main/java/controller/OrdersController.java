package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Agent;
import model.Order;
import model.Product;
import model.TypeStatus;
import service.Service;
import validators.ValidationException;
import window.OrderWindow;

import java.io.IOException;
import java.util.List;

public class OrdersController {


    Service service;
    Agent loggedAgent;
    OrderWindow window;

    public void setService(Service service){
        this.service = service;
    }
    public void setLoggedAgent(Agent agent){this.loggedAgent = agent;}


    public void initiateOrders()
    {
        List<Order> orders = service.getAllOrders(loggedAgent.getId());
        window.initOrdersTable(orders);
    }

    /**
     * Function that open de Main Window and initiate products table
     * @throws IOException
     */
    public void initiateOrdersProcedure() throws IOException {

        Stage stage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/views/orders.fxml"));
        AnchorPane mainLayout = Loader.load();
        window = Loader.getController();
        window.setController(this);
        Scene mainScene = new Scene(mainLayout);
        stage.setScene(mainScene);
        stage.show();
        stage.setWidth(490);
        stage.setHeight(623);
        stage.setTitle("Order Window for " + loggedAgent.getName());

        initiateOrders();

    }

    public void handleCancelOrder(Order order)
    {
        if(order == null)
            throw new ValidationException("You must select an order");
        if(order.getStatus() == TypeStatus.accepted || order.getStatus() == TypeStatus.canceled)
        {
            throw new ValidationException("You have to cancel a pending order");
        }

        service.cancelOrder(order);
        initiateOrders();
    }
}
