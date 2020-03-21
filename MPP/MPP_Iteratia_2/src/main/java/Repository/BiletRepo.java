package Repository;

import Model.Bilet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class BiletRepo implements IRepoBiletUtils<Integer, Bilet> {
    private static final Logger logg = LogManager.getLogger("BiletRepo");
    private DBUtils db ;
    public BiletRepo(){
        db = new DBUtils();
    }

    @Override
    public Bilet findOne(Integer integer) {
        logg.info("ENTERED FUNCTION FindOne");
        if (integer < 0 ){
            logg.info("INVALID ID ");
            return null;
        }
        Connection c = db.getConnection();
        try(PreparedStatement ps = c.prepareStatement("SELECT * FROM BILET WHERE ID = ?")){
            ps.setInt(1, integer);
            ResultSet rs = ps.executeQuery();
            Bilet b;
            while(rs.next()){
                int idBilet = rs.getInt(1);
                String idClient = rs.getString(2);
                int idConcert = rs.getInt(3);
                int nrLocuri = rs.getInt(4);
                b = new Bilet(idBilet, idClient, idConcert, nrLocuri);
                logg.info("Found a ticket with the specified ID");
                return b;
            }
        }catch (Exception e){
            logg.info(e);
        }
        return null;
    }

    @Override
    public Collection<Bilet> findAll() {
        Collection<Bilet> bilete = new ArrayList<>();
        Connection c = db.getConnection();
        try(PreparedStatement ps = c.prepareStatement("SELECT * FROM BILET")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Bilet b = new Bilet(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
                bilete.add(b);
            }
            return bilete;
        } catch (SQLException e) {
            logg.info(e);
        }
        return null;
    }

    @Override
    public int size() {
        int size;
        logg.info("Entered function");
        Connection c = db.getConnection();
        try(PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) AS size FROM Bilet")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                size = rs.getInt("size");
                return size;
            }
        } catch (SQLException e) {
            logg.info(e);
        }
        return 0;
    }

    @Override
    public void saveBilet(Bilet b) {
        int idB = b.getCodBilet();
        int nrLoc = b.getNrLocuri();
        String numeCl = b.getNumeClient();
        int idC = b.getCodConcert();
        Connection con = db.getConnection();
        try(PreparedStatement ps = con.prepareStatement("INSERT INTO BILET VALUES (?,?,?,?)")){
            ps.setInt(1,idB);
            ps.setString(2,numeCl);
            ps.setInt(3,idC);
            ps.setInt(4,nrLoc);
            ps.executeUpdate();
        } catch (SQLException e) {
            logg.info(e.getMessage());
        }
    }

    @Override
    public void save(Bilet bilet) {
    }

    @Override
    public Bilet delete(Integer integer) {
        return null;
    }

    @Override
    public Bilet update(Bilet nou) {
        return null;
    }

}
