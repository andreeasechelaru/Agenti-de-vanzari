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
    public Agent login(Agent agentLogged) throws ValidationException
    {
        agentValidator.validate(agentLogged);
        String hashedPassword  = hashPassword(agentLogged.getPassword());
        agentLogged = agentRepo.findByUsernameAndPassword(agentLogged.getUsername(), hashedPassword);
        if(agentLogged == null)
            throw new ValidationException("This agent doesn't exist!");
        return agentLogged;
    }

    public void logout(){

    }

}