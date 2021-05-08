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

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class MainWindow {
    @FXML
    public Label agentLogged;
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

    public String typeProductName()
    {
        return productNameField.getText();
    }

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
}
