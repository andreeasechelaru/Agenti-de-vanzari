package repository;

import model.Order;
import model.Product;
import model.Tuple;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderRepository extends IRepository<Integer, Order> {

    int findAfterDate(LocalDateTime date);
    void addProducts(Order order);
    List<Tuple> findProductsOrder(Order order);
    void deleteProducts(Order order);
    void updateImportance(Integer id, Order order);
}
