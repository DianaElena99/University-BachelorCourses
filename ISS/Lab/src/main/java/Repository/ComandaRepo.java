package Repository;

import Model.Comanda;
import Model.Medicament;
import Model.MedicamentComanda;
import Model.MedicamentDTO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComandaRepo {
    private static SessionFactory factory;
    public List<Comanda> comenzi;

    public ComandaRepo(){
        comenzi = getAll();
    }

    public List<Comanda> getAll(){
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Transaction tx = null;
        List<Comanda> coms = new ArrayList<>();
        Session sesiune = factory.openSession();
        try{
            tx = sesiune.beginTransaction();
            List users = sesiune.createQuery("FROM Comanda").list();
            for(Iterator it = users.iterator(); it.hasNext();){
                Comanda u = (Comanda) it.next();
                coms.add(u);
            }
            tx.commit();
            return coms;
        }catch (Exception e){
            e.printStackTrace();
            sesiune.close();
        }
        return null;
    }

    public void addMedicamenteComanda(int idComanda, List<Medicament> lista){
        List<MedicamentComanda> meds = new ArrayList<>();
        for (Medicament md : lista
             ) {
            MedicamentComanda nou = new MedicamentComanda(idComanda, md.getId(), md.getCantitateDisp());
            meds.add(nou);
        }
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
            for(MedicamentComanda dto : meds){
                sesiune.save(dto);
            }
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            sesiune.close();
        }
    }

    public void addComanda(Comanda cmd) {
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
            sesiune.save(cmd);
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            sesiune.close();
        }

        addMedicamenteComanda(cmd.getCod(), cmd.getMedicamentList());
    }

    public void removeComanda(Comanda cmd) {
        removeMedicamenteComanda(cmd.getCod());

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
            String hql = "DELETE FROM Comanda WHERE id=:cod";
            Query q = sesiune.createQuery(hql);
            q.setParameter("cod",cmd.getCod());
            q.executeUpdate();
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            sesiune.close();
        }

    }

    public void removeMedicamenteComanda(int cod){
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
            String hql = "FROM MedicamentComanda WHERE codComanda=:cod";
            Query query = sesiune.createQuery(hql);
            query.setParameter("cod", cod);
            List<MedicamentComanda> lista = query.list();
            for (MedicamentComanda md : lista){
                sesiune.delete(md);
            }
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            sesiune.close();
        }

    }
}
