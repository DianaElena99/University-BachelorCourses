package Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepo {
    public DBUtils dbUtil;
    public UserRepo(){
        dbUtil = new DBUtils();
    }

    public int autentificare(String user, String pass){
        int id = -1;
        Connection c = dbUtil.getConnection();
        try(PreparedStatement ps = c.prepareStatement("SELECT id FROM User where nume = ? and parola = ?")){
            ps.setString(1,user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                id = rs.getInt(1);
            }
        }catch (SQLException s){
            s.printStackTrace();
        }
        return id;
    }
}
