package model;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cart{
    private HashMap<Integer, Integer> products;
    private float totalPrice;
    private int discount;

    public Cart()
    {
        this.products = new HashMap<>();
        totalPrice = 0;
        discount = 0;
    }
    public Cart(HashMap<Integer, Integer> products, float totalPrice, int discount) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.discount = discount;
    }


    public HashMap<Integer, Integer> getProducts() {
        return products;
    }

    public void addProduct(Product product, int quantity)
    {
        if(products.containsKey(product.getId()))
        {
            int oldQuantity = products.get(product.getId());
            int newQuantity = oldQuantity + quantity;
            products.remove(product.getId());
            products.put(product.getId(), newQuantity);
        }
        else{
            products.put(product.getId(), quantity);
        }
        totalPrice = totalPrice + product.getPrice() * quantity;
    }

    public void removeProduct(Product product)
    {
        int quantity = products.get(product.getId());
        float price = product.getPrice();

        totalPrice -= price*quantity;
        products.remove(product.getId());
    }

    public void updateProducts(Product product, int newQuantity)
    {
        int oldQuantity = products.get(product.getId());
        if(newQuantity > oldQuantity)
        {
            int diff = newQuantity-oldQuantity;
            //add products
            totalPrice += product.getPrice() * diff;
        }else{
            int diff = oldQuantity-newQuantity;
            // remove products
            totalPrice -= product.getPrice() * diff;
        }
        product.setQuantity(newQuantity);
        products.remove(product.getId());
        products.put(product.getId(), newQuantity);
    }
    public void setProducts(HashMap<Integer, Integer> products) {
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

    public int getTotalProducts(){
        Iterator it = products.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            count += (Integer)pair.getValue();
        }
        return count;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "products=" + products +
                ", totalPrice=" + totalPrice +
                ", discount=" + discount +
                '}';
    }
}
