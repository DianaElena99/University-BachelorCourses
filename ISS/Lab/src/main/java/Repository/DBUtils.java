package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private Connection con = null;

    public DBUtils(){

    }

    private Connection getNewConnection(){
        String connString = "jdbc:mysql://localhost/farmacieiss";
        Connection conn = null;
        try{
            System.out.println("Trying to connect to MYSQL DB");
            conn = DriverManager.getConnection(connString, "root", "");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public Connection getConnection(){
        try{
            if (con == null || con.isClosed()) {
                con = getNewConnection();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return con;
    }
}