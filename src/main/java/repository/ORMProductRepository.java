package repository;

import model.Product;

public class ORMProductRepository implements IProductRepository{
    @Override
    public Product findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Product> findAll() {
        return null;
    }

    @Override
    public void add(Product entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Product entity) {

    }
}
