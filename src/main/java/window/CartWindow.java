package window;

import controller.CartController;
import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product;
import validators.ValidationException;

import java.util.List;

public class CartWindow {



    @FXML
    public Label productsSize;
    @FXML
    public Label totalPrice;
    @FXML
    public Spinner quantitySpinner;
    @FXML
    public TextField discountField;
    //products table
    private ObservableList<Product> model_products = FXCollections.observableArrayList();
    @FXML
    public TableView<Product> tableProducts;
    @FXML
    public TableColumn<Product, String> nameColumn;
    @FXML
    public TableColumn<Product, Float> priceColumn;
    @FXML
    public TableColumn<Product, Integer> quantityColumn;

    MainController mainController;
    CartController cartController;
    public void setController(CartController controller, MainController mainController){
        this.cartController = controller;
        this.mainController = mainController;
    }


    public void initProductsTable(List<Product> products)
    {
        model_products.setAll(products);
    }

    @FXML
    public void initialize() {
        //products table
        nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantity"));
        tableProducts.setItems(model_products);
    }


    public void updateCartInfo(float price, int size){
        totalPrice.setText(String.valueOf(price));
        productsSize.setText(String.valueOf(size));
    }

    public Product selectProduct()
    {
        return tableProducts.getSelectionModel().getSelectedItem();
    }

    public int selectQuantity()
    {
        return (Integer)quantitySpinner.getValue();
    }

    public void clickUpdateQuantityButton()
    {
        try{
            cartController.handleUpdateQuantity(selectProduct(), selectQuantity());
        }catch (ValidationException e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Quantity error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.setHeight(300);
            errorAlert.showAndWait();
        }
    }


    public String insertDiscount()
    {
        return discountField.getText();
    }

    /**
     * Apply a discount to the order
     * @return
     */
    public void clickApplyDiscountButton() {
        try{
            String ds = insertDiscount();
            int d = Integer.parseInt(ds);
            cartController.handleApplyDiscount(d);
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("Discount added");
            info.showAndWait();
        }catch (ValidationException | NumberFormatException e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Discount error, Discount should be integer ");
            errorAlert.setContentText(e.getMessage());
            errorAlert.setHeight(300);
            errorAlert.showAndWait();
        }
    }

    /**
     * Place the order with products from the current cart
     */
    public void clickPlaceTheOrderButton() {
        try{
            cartController.handlePlaceOrder();

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("Successful placed the order");
            info.showAndWait();
        }catch (ValidationException | NumberFormatException e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Order placed error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.setHeight(300);
            errorAlert.showAndWait();
        }
    }
}
