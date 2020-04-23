package Repository;

import Model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepo {
    private static SessionFactory factory;

    public List<User> users;

    public UserRepo(){
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        this.users = getAll();
    }

    public User checkCredentials(String email, String pass){
        try{
            factory = new Configuration().configure().buildSessionFactory();
            Transaction tx = null;
            List<User> usrs = new ArrayList<>();
            Session sesiune = factory.openSession();
            try{
                tx = sesiune.beginTransaction();
                String sql = "FROM User U WHERE U.email =:email and U.passwd =:pass";
                Query q = sesiune.createQuery(sql);
                q.setParameter("email", email);
                q.setParameter("pass", pass);
                List users = q.list();
                for(Iterator it = users.iterator(); it.hasNext();){
                    User u = (User)it.next();
                    usrs.add(u);
                }
                tx.commit();
                return (User)usrs.remove(0);
            }catch (Exception e){
                e.printStackTrace();
                sesiune.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public User deleteUser(User u){
        try{
            factory = new Configuration().configure().buildSessionFactory();
            Transaction tx = null;
            List<User> usrs = new ArrayList<>();
            Session sesiune = factory.openSession();
            try{
                tx = sesiune.beginTransaction();
                sesiune.delete(u);
                tx.commit();
                return u;
            }catch (Exception e){
                e.printStackTrace();
                sesiune.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User u){
        //todo
    }

    public User findUser(String email){
        try{
            factory = new Configuration().configure().buildSessionFactory();
            Transaction tx = null;
            List<User> usrs = new ArrayList<>();
            Session sesiune = factory.openSession();
            try{
                tx = sesiune.beginTransaction();
                String hql = "FROM User U WHERE U.email =:email";
                Query query = sesiune.createQuery(hql);
                query.setParameter("email", email);
                User u = (User) query.list().get(0);
                tx.commit();
                return u;
            }catch (Exception e){
                e.printStackTrace();
                sesiune.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsersBySection(String sectie){
        List<User> useri = new ArrayList<>();
        try{
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            factory = configuration.buildSessionFactory(serviceRegistry);
            Transaction tx = null;
            List<User> usrs = new ArrayList<>();
            Session sesiune = factory.openSession();
            try{
                tx = sesiune.beginTransaction();
                String hql = "FROM User U WHERE U.section =:sectie";
                Query query = sesiune.createQuery(hql);
                query.setParameter("sectie", sectie);
                useri = query.list();
                tx.commit();
                return useri;
            }catch (Exception e){
                e.printStackTrace();
                sesiune.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsersByRol(String rol){
        return null;//todo
    }

    public boolean addNewUser(User u){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(u);
            tx.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            if(tx!=null)
                tx.rollback();
        }
        finally {
            session.close();
        }
        return false;
    }

    public List<User> getAll(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Transaction tx = null;
        List<User> usrs = new ArrayList<>();
        Session sesiune = factory.openSession();
        try{
            tx = sesiune.beginTransaction();
            List users = sesiune.createQuery("FROM User").list();
            for(Iterator it = users.iterator(); it.hasNext();){
                User u = (User)it.next();
                usrs.add(u);
            }
            tx.commit();
            return usrs;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sesiune.close();
        }
        return null;
    }
}
