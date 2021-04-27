package model;

import java.util.List;

public class Company extends Entity<Integer>{
    private String name;
    private String location;
    private String CEO;
    private List<Agent> agents;
    private List<Product> products;

    public Company(String name, String location, String CEO, List<Agent> agents, List<Product> products) {
        this.name = name;
        this.location = location;
        this.CEO = CEO;
        this.agents = agents;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCEO() {
        return CEO;
    }

    public void setCEO(String CEO) {
        this.CEO = CEO;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
