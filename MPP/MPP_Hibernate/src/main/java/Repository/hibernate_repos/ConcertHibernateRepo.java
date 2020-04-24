package Repository.hibernate_repos;

import Model.Concert;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcertHibernateRepo {

    private static SessionFactory factory;

    public ConcertHibernateRepo(){
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
    }

    public void updateLocuriLibere(int idConcert, int nrNou){

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
            Query q = sesiune.createQuery("UPDATE Concert c SET c.nrLocuri = :nr WHERE c.id=:id");
            q.setParameter("id", idConcert);
            q.setParameter("nr", nrNou);
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

    public Concert findOne(int id){
        Concert c = null;

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
            Query q = sesiune.createQuery("FROM Concert c where c.id = :id");
            q.setParameter("id", id);
            c = (Concert) q.list().get(0);
            tx.commit();
        }
        catch (Exception e){
            e.printStackTrace();
            if (tx!=null) tx.rollback();
        }finally {
            sesiune.close();
        }

        return  c;
    }


}
