package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Agent;
import model.Cart;
import model.Product;
import service.Service;
import validators.ValidationException;
import window.LoginWindow;
import window.MainWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController {

    Service service;
    Agent loggedAgent;
    MainWindow window;

    Cart curentCart = new Cart();

    public void setService(Service service){
        this.service = service;
    }
    public void setLoggedAgent(Agent agent){this.loggedAgent = agent;}
    public void updateCurrentCart(Cart cart){
        this.curentCart = cart;
        window.updateCart(cart.getTotalProducts());
    }

    /**
     * Function that initiate the list of all products in order to display them in corresponding table
     */
    public void initiateProducts()
    {
        List<Product> products = service.getAllProducts();
        window.initProductsTable(products);
    }

    /**
     * Function that open de Main Window and initiate products table
     * @throws IOException
     */
    public void initiateMainProcedure() throws IOException {

        Stage stage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/views/mainView.fxml"));
        BorderPane mainLayout = Loader.load();
        window = Loader.getController();
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

        initiateProducts();

    }

    /**
     * Function that handle the search button that return a list of products that contains a given string
     * @param productName String
     * @return products List<Product>
     */
    public List<Product> handleSearchProduct(String productName)
    {
        return service.searchProductsAfterName(productName);
    }

    /**
     * Add a selected product and quantity in the logged agent's cart
     * @param product Product - the selected product from table
     * @param quantity Integer - the selected quantity of the product
     * Throw Validation Exception if the agent didn't select a product or selected an invalid quantity
     */
    public void handleAddToCart(Product product, int quantity)
    {
        if(product == null)
        {
            throw new ValidationException("You must select a product!");
        }
        if(quantity == 0)
        {
            throw new ValidationException("You have to select a quantity != 0");
        }
        if(quantity > product.getQuantity())
        {
            throw new ValidationException("There is not that much quantity of the selected product. Select a smaller one.");
        }
        // update current cart
        curentCart.addProduct(product, quantity);
        // update the quantity available on stock for the selected product
        service.productAddedToCart(product, quantity);
        // update fields for agent window
        window.updateCart(curentCart.getTotalProducts());
        initiateProducts();
    }

    public void handleSeeCart() throws IOException {
        //open cart window
        CartController cartController = new CartController();
        cartController.setService(service);
        cartController.setLoggedAgent(loggedAgent);
        cartController.setCart(curentCart);
        cartController.setMainController(this);
        cartController.initiateCartProcedure();
    }

    public void handleMyOrders() throws IOException {
        //open orders window
        OrdersController ordersController = new OrdersController();
        ordersController.setService(service);
        ordersController.setLoggedAgent(loggedAgent);
        ordersController.initiateOrdersProcedure();
    }
}
