package repo;

import Model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepo {
    private static SessionFactory factory;

    public UserRepo(){
        Logger.getLogger("hibernate.org").setLevel(Level.OFF);
    }

    public int autentificare(String user, String pass){
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
            Query q = sesiune.createQuery("FROM User u where u.parola = :pass and u.nume=:nume");
            q.setParameter("pass", pass);
            q.setParameter("nume", user);
            User usr = (User) q.list().get(0);
            rez = 1;
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
}
