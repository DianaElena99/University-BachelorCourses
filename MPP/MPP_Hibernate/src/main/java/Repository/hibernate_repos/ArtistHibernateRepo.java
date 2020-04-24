package Repository.hibernate_repos;

import Model.Artist;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArtistHibernateRepo {
    private static SessionFactory factory;

    public ArtistHibernateRepo(){
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public void save(Artist a){
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
            sesiune.save(a);
            tx.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }finally {
            sesiune.close();
        }
    }

    public void delete(int id){
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
            Query q = sesiune.createQuery("DELETE FROM Artist A where A.id = :id");
            q.setParameter("id", id);
            q.executeUpdate();
            tx.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }finally {
            sesiune.close();
        }
    }

    public List<Artist> getAll(){
        List<Artist> artists = new ArrayList<>();
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
            Query q = sesiune.createQuery("FROM Artist a");
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

    public Artist findById(int id){
        Artist a = null;
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
            Query q = sesiune.createQuery("FROM Artist A Where A.id=:id");
            q.setParameter("id", id);
            a = (Artist)q.list().toArray()[0];
            tx.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }finally {
            sesiune.close();
        }
        return a;
    }

    public Artist find(String nume) {
        Artist a = null;
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
            Query q = sesiune.createQuery("FROM Artist A Where A.nume=:nume");
            q.setParameter("nume", nume);
            a = (Artist)q.list().toArray()[0];
            tx.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }finally {
            sesiune.close();
        }
        return a;
    }
}
