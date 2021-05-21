package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Agent;
import model.Order;
import model.TypeImportance;
import model.TypeStatus;
import service.Service;
import validators.ValidationException;
import window.OrderWindow;

import java.io.IOException;
import java.util.List;

public class OrdersController {


    Service service;
    Agent loggedAgent;
    OrderWindow orderWindow;

    public void setService(Service service){
        this.service = service;
    }
    public void setLoggedAgent(Agent agent){this.loggedAgent = agent;}


    public void initiateOrders()
    {
        List<Order> orders = service.getAllOrders(loggedAgent.getId());
        orderWindow.initOrdersTable(orders);
    }

    /**
     * Function that open de Order Window and initiate orders table
     * @throws IOException
     */
    public void initiateOrdersProcedure() throws IOException {

        Stage stage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/views/orders.fxml"));
        AnchorPane mainLayout = Loader.load();
        orderWindow = Loader.getController();
        orderWindow.setController(this);
        Scene mainScene = new Scene(mainLayout);
        stage.setScene(mainScene);
        stage.show();
        stage.setWidth(490);
        stage.setHeight(623);
        stage.setTitle("Order Window for " + loggedAgent.getName());

        initiateOrders();

    }

    /**
     * The agent cancel an order and so the program will delete it from database and that it will refresh the table
     * @param order the selected order which will be deleted
     */
    public void handleCancelOrder(Order order)
    {
        if(order == null)
            throw new ValidationException("You must select an order");
        if(order.getStatus() == TypeStatus.accepted)
        {
            throw new ValidationException("You have to cancel a pending order");
        }

        service.cancelOrder(order);
        initiateOrders();
    }

    /**
     * The agent wants to update an order making it urgent. Finally the program will refresh the table.
     * @param order the selected order which will be updated to urgent
     */
    public void handleUrgentOrder(Order order) {
        if(order == null)
            throw new ValidationException("You must select an order");
        if(order.getStatus() == TypeStatus.accepted)
        {
            throw new ValidationException("You have to urgent a pending order");
        }
        if(order.getImportance() == TypeImportance.urgent)
        {
            throw new ValidationException("You have to urgent a medium order");
        }

        service.urgentOrder(order);
        initiateOrders();
    }
}
