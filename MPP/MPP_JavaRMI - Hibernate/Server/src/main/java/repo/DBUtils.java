package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DBUtils {
    private Connection con = null;

    public DBUtils(){

    }

    private Connection getNewConnection(){
        String connString ="";

        try{
            Properties pr = new Properties();
            connString = "jdbc:sqlite:C:/Users/user/Desktop/sqlite-tools-win32-x86-3310100/sqlite-tools-win32-x86-3310100/test.db";

        }catch (Exception e){
            System.out.println("Nu merge");
        }
        //connectionString = "jdbc:sqlite:C:/Users/user/Desktop/sqlite-tools-win32-x86-3310100/sqlite-tools-win32-x86-3310100/test.db";
        Connection conn = null;
        try{
            System.out.println("Trying to connect to SQLite DB");
            conn = DriverManager.getConnection(connString);
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