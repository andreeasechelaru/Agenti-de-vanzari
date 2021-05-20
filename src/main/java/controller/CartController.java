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
import window.CartWindow;
import window.MainWindow;

import java.io.IOException;
import java.util.List;

public class CartController {

    Service service;
    Agent loggedAgent;
    CartWindow window;
    MainController mainController;
    Cart curentCart;

    public void setService(Service service){
        this.service = service;
    }
    public void setLoggedAgent(Agent agent){this.loggedAgent = agent;}
    public void setCart(Cart cart){this.curentCart = cart;}
    public void setMainController(MainController mainController){this.mainController = mainController;}

    public void initiateCart()
    {
        List<Product> products = service.getAllProductsFromCart(curentCart);
        window.initProductsTable(products);
        window.updateTotalPrice(curentCart.getTotalPrice());
        window.updateProductsSize(curentCart.getTotalProducts());
    }

    public void initiateCartProcedure() throws IOException {
        Stage stage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/views/cartView.fxml"));
        AnchorPane mainLayout = Loader.load();
        window = Loader.getController();
        window.setController(this, mainController);
        Scene mainScene = new Scene(mainLayout);
        stage.setScene(mainScene);
        stage.show();
        stage.setWidth(719);
        stage.setHeight(483);
        stage.setTitle("Cart Window for " + loggedAgent.getName());

        initiateCart();

    }

    public void handleUpdateQuantity(Product product, int quantity)
    {
        if(product == null)
            throw new ValidationException("You have to select a product");

        if(quantity == 0)
        {
            System.out.println("Quantity is 0");
            // product is removed from cart
            curentCart.removeProduct(product);
            System.out.println("Current cart after removing product");
            System.out.println(curentCart);
            service.updateQuantity(product, product.getQuantity(), 0);



        }
        else{
            int dbQuantity = service.getDBQuantity(product);
            System.out.println(dbQuantity);
            if(quantity > dbQuantity)
            {
                throw new ValidationException("There is not so much of this product in stock. Try maximum " + dbQuantity);
            }


            service.updateQuantity(product, product.getQuantity(), quantity);
            curentCart.updateProducts(product, quantity);
        }
        mainController.initiateProducts();
        mainController.updateCurrentCart(curentCart);

        window.updateProductsSize(curentCart.getTotalProducts());
        window.updateTotalPrice(curentCart.getTotalPrice());
        initiateCart();
    }

    public void handleApplyDiscount(int discount)
    {
        if(discount >= 100)
            throw new ValidationException("Discount should be between 0 and 100");
        curentCart.setDiscount(discount);
    }

    public void handlePlaceOrder()
    {
        service.placeOrder(loggedAgent, curentCart);
    }


}
