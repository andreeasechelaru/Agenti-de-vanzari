package repository;


import model.*;

import repository.IOrderRepository;
import repository.JdbcUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ORMOrderRepository implements IOrderRepository {

    private JdbcUtils dbUtils;


    public ORMOrderRepository(Properties props) {
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public Order findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Order> findAll() {
        Connection con = dbUtils.getConnection();
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select * from orders")) {
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("ID");
                    LocalDateTime date = LocalDateTime.parse(result.getString("dateTime"));
                    TypeStatus status = TypeStatus.valueOf(result.getString("status"));
                    Float total = result.getFloat("total");
                    int agent = result.getInt("agent");
                    TypeImportance importance = TypeImportance.valueOf(result.getString("importance"));
                    Order o = new Order(agent, null, date, status, total, importance);
                    o.setId(id);
                    orders.add(o);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return orders;
    }

    @Override
    public void add(Order entity) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("insert into orders (dateTime, status, total, agent, importance) values (?,?,?,?,?)")){
            preStmt.setString(1, entity.getDateTime().toString());
            preStmt.setString(2, entity.getStatus().toString());
            preStmt.setFloat(3, entity.getTotal());
            preStmt.setInt(4, entity.getAgent());
            preStmt.setString(5, entity.getImportance().toString());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error DB " + ex);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from orders where ID = ?")){
            preStmt.setInt(1, integer);
            int result = preStmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public void update(Integer integer, Order entity) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update orders set status = ? where ID = ?")){
            preStmt.setString(1, entity.getStatus().toString());
            preStmt.setInt(2, integer);
            int result = preStmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public int findAfterDate(LocalDateTime date) {
        Connection con = dbUtils.getConnection();
        int id = -1;
        try(PreparedStatement preStmt = con.prepareStatement("select ID from orders where dateTime = ?")){
            preStmt.setString(1, date.toString());
            try(ResultSet result = preStmt.executeQuery()) {
                id = result.getInt("ID");
            }
        }catch (SQLException e){
            System.err.println("Error DB " + e);
        }
        return id;
    }

    @Override
    public void addProducts(Order order) {
        Connection con = dbUtils.getConnection();
        for(Product p : order.getProducts())
        {
            try(PreparedStatement preStmt = con.prepareStatement("insert into orderedProducts (o_id, p_id, quantity) values (?,?,?)")){
                preStmt.setInt(1, order.getId());
                preStmt.setInt(2, p.getId());
                preStmt.setInt(3, p.getQuantity());
                int result = preStmt.executeUpdate();
            } catch (SQLException ex) {
                System.err.println("Error DB " + ex);
            }
        }

    }

    @Override
    public List<Tuple> findProductsOrder(Order order) {
        Connection con = dbUtils.getConnection();
        List<Tuple> products = new ArrayList<>();
        try(PreparedStatement preStmt = con.prepareStatement("select p_id, quantity from orderedProducts where o_id = ?")) {
            preStmt.setInt(1, order.getId());
            try(ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int p_id = result.getInt("p_id");
                    int quantity = result.getInt("quantity");
                    Tuple t = new Tuple();
                    t.setLeft(p_id);
                    t.setRight(quantity);
                    products.add(t);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error DB " + e);
        }
        return products;
    }

    @Override
    public void deleteProducts(Order order) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("delete from orderedProducts where o_id = ?")){
            preStmt.setInt(1, order.getId());
            int result = preStmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("Error DB " + e);
        }
    }

    @Override
    public void updateImportance(Integer id, Order order) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt = con.prepareStatement("update orders set importance = ? where ID = ?")){
            preStmt.setString(1, order.getImportance().toString());
            preStmt.setInt(2, id);
            int result = preStmt.executeUpdate();
        }catch (SQLException e){
            System.err.println("Error DB " + e);
        }
    }
}
