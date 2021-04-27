package repository;

import model.Order;

public class ORMOrderRepository implements IOrderRepository{
    @Override
    public Order findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Order> findAll() {
        return null;
    }

    @Override
    public void add(Order entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Order entity) {

    }
}
