package Repository.hibernate_repos;

import Utils.GetPropertyValues;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
        private static final Logger logg = LogManager.getLogger("DBUtils Logger ");
        private Connection con = null;

        public DBUtils(){

        }

        private Connection getNewConnection(){
            logg.traceEntry();
            String connString ="";
            String usr = "";
            try{
                connString = new GetPropertyValues().getPropValues().getProperty("database");
                usr = new GetPropertyValues().getPropValues().getProperty("user");
                logg.info(connString);
            }catch (Exception e){
                logg.info("Nu merge...");
            }
            Connection conn = null;
            try{
                logg.info("Trying to connect to mysql");
                conn = DriverManager.getConnection(connString, usr, "");
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

