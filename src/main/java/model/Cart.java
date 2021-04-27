package model;

import java.util.HashMap;

public class Cart{
    private HashMap<Product, Integer> products;
    private float totalPrice;
    private int discount;

    public Cart(HashMap<Product, Integer> products, float totalPrice, int discount) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }


    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
