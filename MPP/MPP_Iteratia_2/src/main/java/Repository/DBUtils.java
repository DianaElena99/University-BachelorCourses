package Repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Utils.GetPropertyValues;

public class DBUtils {
    private static final Logger logg = LogManager.getLogger("DBUtils Logger ");
    private Connection con = null;

    public DBUtils(){

    }

    private Connection getNewConnection(){
        logg.traceEntry();
        String connString ="";

        try{
            connString = new GetPropertyValues().getPropValues().getProperty("database");
            logg.info(connString);
        }catch (Exception e){
           logg.info("Nu merge...");
        }
        //String connString = "jdbc:sqlite:C:/Users/user/Desktop/sqlite-tools-win32-x86-3310100/sqlite-tools-win32-x86-3310100/test.db";
        Connection conn = null;
        try{
            logg.info("Trying to connect to sqlite db");
            conn = DriverManager.getConnection(connString);
        } catch (Exception e) {
            logg.error(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    public Connection getConnection(){
        logg.traceEntry();
        try{
            if (con == null || con.isClosed()) {
                con = getNewConnection();
            }
        } catch (SQLException e) {
            logg.error(e.getMessage());
            e.printStackTrace();
        }
        logg.traceExit(con);
        return con;
    }
}
