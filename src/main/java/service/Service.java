package service;


import model.*;
import repository.IAgentRepository;
import repository.IOrderRepository;
import repository.IProductRepository;
import repository.ORMAgentRepository;
import sun.security.validator.ValidatorException;
import validators.IValidator;
import validators.ValidationException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.*;

public class Service {

    private IAgentRepository agentRepo;
    private IProductRepository productRepo;
    private IOrderRepository orderRepo;
    private IValidator<Agent> agentValidator;
    private IValidator<Product> productValidator;
    private IValidator<Order> orderValidator;

    private Cart currentCart = new Cart();

    public Service(IAgentRepository agentRepo, IProductRepository productRepo, IOrderRepository orderRepo, IValidator<Agent> agentValidator, IValidator<Product> productValidator, IValidator<Order> orderValidator) {
        this.agentRepo = agentRepo;
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.agentValidator = agentValidator;
        this.productValidator = productValidator;
        this.orderValidator = orderValidator;
    }

    /***
     * function that returns the password hashed
     * @param password String
     * @return hashedPassword String
     */
    public String hashPassword(String password)
    {
        try{
            byte[] bytes = password.getBytes();
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] digest = m.digest(bytes);
            String hash = new BigInteger(1, digest).toString(16);
            return hash;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     * Function that validate the agent' username and password and return the agent found in database corresponding to it's credentials
     * @param agentLogged Agent
     * @return agentLogged Agent
     * @throws ValidationException
     */
    public Agent login(Agent agentLogged) throws ValidationException
    {
        agentValidator.validate(agentLogged);
        String hashedPassword  = hashPassword(agentLogged.getPassword());
        agentLogged = agentRepo.findByUsernameAndPassword(agentLogged.getUsername(), hashedPassword);
        if(agentLogged == null)
            throw new ValidationException("This agent doesn't exist!");
        return agentLogged;
    }

    /***
     * Function that returns all products from database
     * @return products List<Product>
     */
    public List<Product> getAllProducts()
    {
        List<Product> products = new ArrayList<>();
        for(Product product : productRepo.findAll())
        {
            products.add(product);
        }
        return products;
    }

    /***
     * Function that returns all products from database
     * @return products List<Product>
     */
    public List<Order> getAllOrders(int agent)
    {
        List<Order> orders = new ArrayList<>();
        for(Order order : orderRepo.findAll())
        {
            if(order.getAgent() == agent)
            {
                List<Tuple> productsTuple = orderRepo.findProductsOrder(order);
                List<Product> products = new ArrayList<>();
                for(Tuple pId : productsTuple)
                {
                    Product p = productRepo.findOne(pId.getLeft());
                    p.setQuantity(pId.getRight());
                    products.add(p);
                }
                order.setProducts(products);
                orders.add(order);
            }
        }
        return orders;
    }

    /***
     * Function that search products from database that contain a given string in their name
     * @param name String
     * @return products List<Product>
     */
    public List<Product> searchProductsAfterName(String name){
        List<Product> products = new ArrayList<>();
        for(Product product : productRepo.findAll())
        {
            if(product.getName().toLowerCase().contains(name.toLowerCase()))
                products.add(product);
        }
        return products;
    }

    public void productAddedToCart(Product product, int quantity){
        product.setQuantity(product.getQuantity() - quantity);
        productRepo.update(product.getId(), product);
    }

    public List<Product> getAllProductsFromCart(Cart cart)
    {
        Iterator it = cart.getProducts().entrySet().iterator();
        List<Product> products = new ArrayList<>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            int pId = (Integer) pair.getKey();
            Product p = productRepo.findOne(pId);
            p.setQuantity((Integer)pair.getValue());
            products.add(p);
        }
        return products;
    }

    public void updateQuantity(Product product, int oldQuantity, int newQuantity) {
        if(newQuantity < oldQuantity)
        {
            System.out.println("Put product back");
            //put products back to database
            int quantity = oldQuantity-newQuantity;
            int dbQuantity = getDBQuantity(product);
            product.setQuantity(dbQuantity + quantity);
            productRepo.update(product.getId(), product);
            System.out.println("Product updated");
        }else{
            System.out.println("Add product from bd");
            //put products back to database
            int quantity = newQuantity-oldQuantity;
            int dbQuantity = getDBQuantity(product);
            product.setQuantity(dbQuantity - quantity);
            productRepo.update(product.getId(), product);
            System.out.println("Product updated");
        }
    }

    public int getDBQuantity(Product p){
        System.out.println("Service");
        Product p1 = productRepo.findOne(p.getId());
        return p1.getQuantity();
    }

    public void placeOrder(Agent agent, Cart cart)
    {
        Order order = new Order();
        order.setAgent(agent.getId());
        order.setProducts(getAllProductsFromCart(cart));
        int discount = cart.getDiscount();
        float total = cart.getTotalPrice();
        System.out.println("New order created ...");
        order.setTotal(total - ((discount * total) / 100));
        order.setDateTime(LocalDateTime.now());
        order.setStatus(TypeStatus.pending);
        System.out.println(order);
        orderRepo.add(order);
        int id = orderRepo.findAfterDate(order.getDateTime());
        order.setId(id);
        orderRepo.addProducts(order);

        Timer t = new java.util.Timer();
        t.schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        int ok = 0;
                        List<Order> orders = getAllOrders(1);
                        System.out.println("All orders: ");
                        for(Order o : orders)
                            System.out.println(o);
                        while(ok == 0)
                        {
                            int index = (int)(Math.random() * orders.size());
                            Order order1 = orders.get(index);
                            if(order1.getStatus() == TypeStatus.pending) {
                                ok = 1;
                                order1.setStatus(TypeStatus.accepted);
                                orderRepo.update(order1.getId(), order1);
                            }

                        }
                        t.cancel();
                    }
                },
                10000
        );

    }

    public void cancelOrder(Order order)
    {
        order.setStatus(TypeStatus.canceled);
        orderRepo.update(order.getID(), order);
    }
}