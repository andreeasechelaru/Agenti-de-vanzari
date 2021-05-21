package window;

import controller.LoginController;
import controller.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Product;
import validators.ValidationException;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.List;

public class MainWindow {
    @FXML
    public Label agentLogged;
    @FXML
    public Spinner quantitySelector;
    @FXML
    public Label productsCart;


    MainController controller;

    @FXML
    public TextField productNameField;

    //products table
    private ObservableList<Product> model_products = FXCollections.observableArrayList();
    @FXML
    public TableView<Product> productsTable;
    @FXML
    public TableColumn<Product, String> nameColumn;
    @FXML
    public TableColumn<Product, Float> priceColumn;
    @FXML
    public TableColumn<Product, Integer> quantityColumn;


    public void setController(MainController controller){
        this.controller = controller;
    }
    public void setAgent(String agentsName)
    {
        agentLogged.setText(agentsName);
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
        productsTable.setItems(model_products);
    }

    /**
     * Returns the name of the product inserted by the user
     * @return password String
     */
    public String typeProductName()
    {
        return productNameField.getText();
    }

    /**
     * Handle the agent-logged's click on search button. If the agent click the search button with no name
     * inserted in corresponding field in the table will be displayed all products, else in the table will
     * be showed all products that contains the given string
     * @param actionEvent
     */
    public void clickSearchButton(ActionEvent actionEvent) {
        String productName = typeProductName();
        if(productName.equals(""))
        {
            controller.initiateProducts();
        }
        else{
            List<Product> products = controller.handleSearchProduct(productName);
            initProductsTable(products);
        }
    }

    /**
     * Return the selected product from the table
     * @return Product
     */
    public Product selectProduct()
    {
        return productsTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Return the selected quantity from the spinner
     * @return Integer
     */
    public int selectQuantity()
    {
        return (Integer)quantitySelector.getValue();
    }

    /**
     * Add the selected product and quantity to the agent's cart
     * If the agent forgets to select a product the program will display an error message: You must select a product!
     * If the agent select a bigger quantity (than the one available on stock) the program will display an error message:
     *                              There is not that much quantity of the selected product. Select a smaller quantity
     */
    public void clickAddToCartButton()
    {
        try{
            controller.handleAddToCart(selectProduct(), selectQuantity());
        }catch (ValidationException e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Quantity error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.setHeight(300);
            errorAlert.showAndWait();
        }
    }

    /**
     * Update the field from the window that represents the number of products from the current cart
     * @param products the number of products added by the logged agent in the cart
     */
    public void updateCart(Integer products)
    {
        productsCart.setText(products.toString());
    }

    /**
     * The agent click to see his cart and a new window will show up with information about the current cart
     * throws exception if there is any problem on opening the new window
     */
    public void clickSeeCartButton()
    {
        try{
            controller.handleSeeCart();
        }catch (ValidationException | IOException e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.setHeight(300);
            errorAlert.showAndWait();
        }
    }

    /**
     * The agent click to see his orders
     * @throws IOException
     */
    public void clickMyOrdersButton() throws IOException {
        controller.handleMyOrders();
    }
}
