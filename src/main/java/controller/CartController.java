package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Agent;
import model.Cart;
import model.Product;
import service.Service;
import validators.ValidationException;
import window.CartWindow;

import java.io.IOException;
import java.util.List;

public class CartController {

    Service service;
    Agent loggedAgent;
    CartWindow cartWindow;
    MainController mainController;
    Cart curentCart;

    /**
     * Set all properties needed for the controller
     * @param service Service
     * @param agent Agent - the logged agent
     * @param cart Cart - the current cart
     * @param mainController MainController - to update products from the main window
     */
    public void setProperties(Service service, Agent agent, Cart cart, MainController mainController)
    {
        this.service = service;
        this.loggedAgent = agent;
        this.curentCart = cart;
        this.mainController = mainController;
    }

    /**
     * Initiate table with the products from current cart and update cart info from window (price, length)
     */
    public void initiateCart()
    {
        List<Product> products = service.getAllProductsFromCart(curentCart);
        cartWindow.initProductsTable(products);
        cartWindow.updateCartInfo(curentCart.getTotalPrice(), curentCart.getTotalProducts());

    }

    /**
     * Open the window for the current controller in order to display card information for the logged agent
     * @throws IOException if there is a problem on opening the window
     */
    public void initiateCartProcedure() throws IOException {
        Stage stage = new Stage();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/views/cartView.fxml"));
        AnchorPane mainLayout = Loader.load();
        cartWindow = Loader.getController();
        cartWindow.setController(this, mainController);
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
        initiateCart();
    }

    /**
     * Set discount to current cart if the discount is available (0 < discount < 100)
     * @param discount
     */
    public void handleApplyDiscount(int discount)
    {
        if(discount >= 100)
            throw new ValidationException("Discount should be between 0 and 100");
        curentCart.setDiscount(discount);
    }


    /**
     * Place the order of the current agent with his cart
     */
    public void handlePlaceOrder()
    {
        service.placeOrder(loggedAgent, curentCart);
    }


}
