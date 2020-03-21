package Repository;

import Model.Locatie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.rmi.runtime.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class LocatieRepo implements IRepo<Integer, Locatie> {
    private DBUtils dbutils;
    private static final Logger logg = LogManager.getLogger("LocRepo");

    public LocatieRepo(){
        dbutils = new DBUtils();
    }

    @Override
    public Locatie findOne(Integer integer) {
        logg.info("Entered Find One LocatieRepo");
        Connection c = dbutils.getConnection();
        try(PreparedStatement s = c.prepareStatement("select * from Locatie where ID = ?")){
            s.setInt(1,integer);
            ResultSet rs = s.executeQuery();
            while (rs.next()){
                Locatie l = new Locatie(rs.getInt(1), rs.getString(2), rs.getInt(3));
                return l;
            }
        } catch (SQLException e) {
            logg.info(e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<Locatie> findAll() {
        Collection<Locatie> locatii = new ArrayList<>();
        Connection c = dbutils.getConnection();
        try(PreparedStatement ps = c.prepareStatement("SELECT * FROM LOCATIE")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Locatie l = new Locatie(rs.getInt(1), rs.getString(2), rs.getInt(3));
                locatii.add(l);
            }
            return locatii;
        } catch (SQLException e) {
            logg.info(e.getMessage());
        }
        return null;
    }

    @Override
    public int size() {
        Connection conn = dbutils.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("select count(*) as size from Locatie")){
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    return rs.getInt("size");
                }
            }
        } catch (Exception e) {
            logg.error(e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void save(Locatie locatie) {

    }

    @Override
    public Locatie delete(Integer integer) {
        return null;
    }

    @Override
    public Locatie update(Locatie nou) {
        return null;
    }

}
