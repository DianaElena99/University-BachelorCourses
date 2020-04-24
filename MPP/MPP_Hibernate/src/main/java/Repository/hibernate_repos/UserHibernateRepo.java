package Repository.hibernate_repos;

import Model.Artist;
import Model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserHibernateRepo {

    private static SessionFactory factory;

    public UserHibernateRepo(){
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public int checkCredentials(User u){
        int rez = -1;
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session sesiune = factory.openSession();
        Transaction tx = null;
        try{
            tx = sesiune.beginTransaction();
            Query q = sesiune.createQuery("FROM User u where u.passwd = :pass and u.email=:email");
            q.setParameter("pass", u.getPasswd());
            q.setParameter("email", u.getEmail());
            User usr = (User) q.list().get(0);
            rez = usr.getId();
            tx.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }finally {
            sesiune.close();
        }
        return rez;
    }

    public List<User> getAll(){
        List<User> artists = new ArrayList<>();
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session sesiune = factory.openSession();
        Transaction tx = null;
        try{
            tx = sesiune.beginTransaction();
            Query q = sesiune.createQuery("FROM User u");
            artists = q.list();
            tx.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }finally {
            sesiune.close();
        }
        return artists;
    }

}
