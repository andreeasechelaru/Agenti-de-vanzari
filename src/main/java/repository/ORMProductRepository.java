package repository;

import model.Agent;
import model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ORMProductRepository implements IProductRepository{

    public ORMProductRepository() {
        initialize();
    }

    static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    @Override
    public Product findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Product> findAll() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Product> products =
                        session.createQuery("from Product as u order by u.name asc", Product.class).
                                list();
                System.out.println(products.size() + " product(s) found:");
                tx.commit();
                return products;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
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
