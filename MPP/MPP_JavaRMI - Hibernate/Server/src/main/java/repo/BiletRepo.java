package repo;

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
