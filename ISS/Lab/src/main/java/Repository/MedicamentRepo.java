package Repository;

import Model.Medicament;
import Model.MedicamentComanda;
import Model.MedicamentDTO;
import Model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MedicamentRepo {
    private static SessionFactory factory;
    public List<Medicament> meds;
    public static DBUtils db;

    public MedicamentRepo(){
        meds = getAll();
        db = new DBUtils();
    }

    public List<Medicament> getAll(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Transaction tx = null;
        List<Medicament> meds = new ArrayList<>();
        Session sesiune = factory.openSession();
        try{
            tx = sesiune.beginTransaction();
            List users = sesiune.createQuery("FROM Medicament").list();
            for(Iterator it = users.iterator(); it.hasNext();){
                Medicament u = (Medicament) it.next();
                meds.add(u);
            }
            tx.commit();
            return meds;
        }catch (Exception e){
            e.printStackTrace();
            sesiune.close();
        }
        return null;
    }

    public void updateMedicament(int cod, int i) {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Transaction tx = null;
        Session sesiune = factory.openSession();
        try{
            tx = sesiune.beginTransaction();
            Query q = sesiune.createQuery("UPDATE Medicament m set cantitate=:cant WHERE cod=:cod");
            q.setParameter("cant",i);
            q.setParameter("cod", cod);
            q.executeUpdate();

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            sesiune.close();
        }
    }
}
