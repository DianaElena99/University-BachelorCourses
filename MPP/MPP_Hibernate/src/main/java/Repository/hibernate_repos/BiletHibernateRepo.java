package Repository.hibernate_repos;

import Model.Bilet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BiletHibernateRepo {

    private static SessionFactory factory;

    public  BiletHibernateRepo(){
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);}

    public void save(Bilet b){
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
            sesiune.save(b);
            tx.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }finally {
            sesiune.close();
        }
    }
}
