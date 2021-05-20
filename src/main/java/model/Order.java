package model;

import java.sql.Date;

import java.time.LocalDateTime;
import java.util.List;

//
//@javax.persistence.Entity
//@Table(name = "orders")
//public class Order extends Entity<Integer>{
//    private int agent;
//    private LocalDateTime dateTime;
//    private TypeStatus status;
//    private float total;
//
//    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(name="orderedProducts", joinColumns= {@JoinColumn(name="o_id")},  inverseJoinColumns= {@JoinColumn(name="p_id")})
//    private Set<Product> products = new HashSet();
//
//
//    public Order() {
//    }
//
//    public Order(int agent, Set<Product> products, LocalDateTime dateTime, TypeStatus status, float total) {
//        this.agent = agent;
//        this.products = products;
//        this.dateTime = dateTime;
//        this.status = status;
//        this.total = total;
//    }
//
//    public int getAgent() {
//        return agent;
//    }
//
//    public void setAgent(int agent) {
//        this.agent = agent;
//    }
//
//    public Set<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<Product> products) {
//        this.products = products;
//    }
//
//    public LocalDateTime getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(LocalDateTime dateTime) {
//        this.dateTime = dateTime;
//    }
//
//    public TypeStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(TypeStatus status) {
//        this.status = status;
//    }
//
//    public float getTotal() {
//        return total;
//    }
//
//    public void setTotal(float total) {
//        this.total = total;
//    }
//
//    @Override
//    public String toString() {
//        return "Order{" +
//                "agent=" + agent +
//                ", dateTime=" + dateTime +
//                ", status=" + status +
//                ", total=" + total +
//                ", products=" + products +
//                '}';
//    }
//}




public class Order extends Entity<Integer>{
    private int agent;
    private LocalDateTime dateTime;
    private TypeStatus status;
    private float total;
    private List<Product> products;

    public Order() {
    }

    public Order(int agent, List<Product> products, LocalDateTime dateTime, TypeStatus status, float total) {
        this.agent = agent;
        this.products = products;
        this.dateTime = dateTime;
        this.status = status;
        this.total = total;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getID(){return this.getId();}

    @Override
    public String toString() {
        return "Order{" +
                "agent=" + agent +
                ", dateTime=" + dateTime +
                ", status=" + status +
                ", total=" + total +
                ", products=" + products +
                '}';
    }
}
