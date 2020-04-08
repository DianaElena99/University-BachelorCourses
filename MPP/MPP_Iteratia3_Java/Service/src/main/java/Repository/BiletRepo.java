package Repository;

import Model.Bilet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BiletRepo {
    public DBUtils db;

    public BiletRepo(){
        db = new DBUtils();
    }


    public int size() {
        int size;

        Connection c = db.getConnection();
        try(PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) AS size FROM Bilet")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                size = rs.getInt("size");
                return size;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void saveBilet(Bilet b) {
        int nrLoc = b.getNrLocuri();
        String numeCl = b.getNumeClient();
        int idC = b.getCodConcert();
        Connection con = db.getConnection();
        try(PreparedStatement ps = con.prepareStatement("INSERT INTO BILET VALUES (?,?,?)")){
            ps.setString(1,numeCl);
            ps.setInt(2,idC);
            ps.setInt(3,nrLoc);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
