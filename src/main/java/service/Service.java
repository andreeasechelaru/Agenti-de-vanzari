package service;


import model.Agent;
import model.Order;
import model.Product;
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
import java.util.ArrayList;
import java.util.List;

public class Service {

    private IAgentRepository agentRepo;
    private IProductRepository productRepo;
    private IOrderRepository orderRepo;
    private IValidator<Agent> agentValidator;
    private IValidator<Product> productValidator;
    private IValidator<Order> orderValidator;

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

}