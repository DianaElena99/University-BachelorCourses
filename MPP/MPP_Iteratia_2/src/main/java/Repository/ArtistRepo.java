package Repository;

import Model.Artist;
import Model.Validators.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ArtistRepo implements IRepo<Integer, Artist> {
    private DBUtils db;
    private static final Logger logg = LogManager.getLogger("ArtistLogger");
    public ArtistRepo(){
        db = new DBUtils();
    }

    @Override
    public Artist findOne(Integer integer) {
        logg.info("FIND ARTIST WITH ID " + integer);
        if (integer < 0){
            logg.info("INVALID ID, EXITING FUNCTION");
            return null;
        }
        Artist target;
        Connection c = db.getConnection();
        try(PreparedStatement s = c.prepareStatement("SELECT * FROM ARTIST WHERE ID = ?")){
            s.setInt(1,integer);
            ResultSet rs = s.executeQuery();
            while(rs.next()){
                logg.info("FOUND THE ARTIST");
                String nume = rs.getString("Nume");
                target = new Artist(integer, nume);
                logg.info("EXITING FUNCTION FindOne");
                return target;
            }
        }
        catch (Exception e){
            logg.info(e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<Artist> findAll() {
        logg.info("ENTERED FUNCTION FIND ALL");
        Collection<Artist> rezFin = new ArrayList<>();
        Connection c = db.getConnection();
        try(PreparedStatement st = c.prepareStatement("SELECT * FROM ARTIST")){
            ResultSet r = st.executeQuery();
            while(r.next()){
                logg.info("ADDING ARTISTS TO LIST");
                int id = r.getInt("ID");
                String nume = r.getString("Nume");
                Artist a = new Artist(id, nume);
                logg.info("DONE, EXITING FUNCTION");
                rezFin.add(a);
            }
            return rezFin;
        } catch (SQLException e) {
            logg.info("SOMETHIN WENT WRONG");
        }
        return null;
    }

    @Override
    public int size() {
        logg.info("SIZE OF ArtistRepo");
        int s = 0;
        Connection c = db.getConnection();
        try (PreparedStatement statement = c.prepareStatement("SELECT COUNT(*) AS size FROM ARTIST")){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                s = rs.getInt("size");
                logg.info("SIZE = " + s + ", EXITING FUNCTION");
                return s;
            }
        } catch (SQLException e) {
            logg.info("Something went wrong");
        }
        return s;
    }


    @Override
    public void save(Artist artist) {

    }

    @Override
    public Artist delete(Integer integer) {
        return null;
    }

    @Override
    public Artist update(Artist nou) {
        return null;
    }
}
