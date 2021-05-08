package repository;

import model.Agent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class ORMAgentRepository implements IAgentRepository{

    public ORMAgentRepository() {
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
    public Agent findByUsernameAndPassword(String username, String password) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Agent agent = session.createQuery("from Agent u where u.username=:username and u.password=:password", Agent.class)
                        .setParameter("username", username)
                        .setParameter("password", password)
                        .setMaxResults(1)
                        .uniqueResult();
                tx.commit();
                return agent;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

    @Override
    public Agent findOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Agent> findAll() {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Agent> agents =
                        session.createQuery("from Agent as u order by u.name asc", Agent.class).
                                list();
                System.out.println(agents.size() + " agent(s) found:");
                tx.commit();
                return agents;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
                return null;
            }
        }
    }

    @Override
    public void add(Agent entity) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Agent entity) {

    }


}
