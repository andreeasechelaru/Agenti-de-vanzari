package window;

import controller.CartController;
import controller.MainController;
import controller.OrdersController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.Order;
import model.TypeImportance;
import model.TypeStatus;
import validators.ValidationException;

import java.time.LocalDateTime;
import java.util.List;

public class OrderWindow {


    //products table
    private ObservableList<Order> model_orders = FXCollections.observableArrayList();
    @FXML
    public TableView<Order> tableOrders;
    @FXML
    public TableColumn<Order, Integer> idColumn;
    @FXML
    public TableColumn<Order, LocalDateTime> dateColumn;
    @FXML
    public TableColumn<Order, Float> priceColumn;
    @FXML
    public TableColumn<Order, TypeStatus> statusColumn;
    @FXML
    public TableColumn<Order, TypeImportance> importanceColumn;

    OrdersController orderController;
    public void setController(OrdersController controller){
        this.orderController = controller;
    }
    public void initOrdersTable(List<Order> orders)
    {
        model_orders.setAll(orders);
    }

    @FXML
    public void initialize() {
        //products table
        idColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("ID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Order, LocalDateTime>("dateTime"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Order, Float>("total"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Order, TypeStatus>("status"));
        importanceColumn.setCellValueFactory(new PropertyValueFactory<Order, TypeImportance>("importance"));
        tableOrders.setItems(model_orders);
    }

    public Order selectOrder()
    {
        return tableOrders.getSelectionModel().getSelectedItem();
    }

    /**
     * The order will be removed
     */
    public void clickCancelOrder() {
        try{
            orderController.handleCancelOrder(selectOrder());

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("Successful canceled the order");
            info.showAndWait();
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
     * The order will be updated to urgent
     */
    public void clickMakeUrgentButton(ActionEvent actionEvent) {
        try{
            orderController.handleUrgentOrder(selectOrder());

            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setHeaderText("Successful canceled the order");
            info.showAndWait();
        }catch (ValidationException e)
        {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Quantity error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.setHeight(300);
            errorAlert.showAndWait();
        }
    }
}


