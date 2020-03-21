package Repository;

import Model.TipUser;
import Model.User;
import Model.Validators.UserValidator;
import Model.Validators.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class UserRepo implements IRepo<Integer, User>{
    private Validator<User> valid;
    private static final Logger logg = LogManager.getLogger("UserRepoLogger");
    private DBUtils dbutils = new DBUtils();

    public UserRepo(){
        valid = new UserValidator();
    }

    public int autentificare(String user, String pass){
        int id = -1;
        Connection c = dbutils.getConnection();
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

    @Override
    public void save(User user) {
        logg.info("USER REPO : Adding a user ");
        try {
            valid.validate(user);
        } catch (Exception e) {
            logg.error("ERROR : " + e.getMessage());
            e.printStackTrace();
        }
        if (findOne(user.getCodUser())!=null){
            logg.error("ERROR : User deja existent");
            return;
        }
        Connection c = dbutils.getConnection();
        try(PreparedStatement stmt = c.prepareStatement("INSERT INTO USER VALUES (?,?,?,?,?)")) {
            stmt.setInt(1,user.getCodUser());
            stmt.setString(2,user.getNume());
            stmt.setString(3,user.getEmail());
            stmt.setString(4,user.getParola());
            stmt.setString(5,String.valueOf(user.getRol()));
            stmt.execute();
            logg.info("Success!");
        } catch (SQLException e) {
            logg.error("ERROR : " + e.getMessage());
            e.printStackTrace();
        }
        logg.info("Exiting SAVE function");
    }

    @Override
    public User delete(Integer integer) {
        logg.info("USER REPO : Deleting user with id " + integer);
        User target;
        if (integer < 0){
            logg.info("USER REPO : Invalid ID");
            return null;
        }

        target = findOne(integer);
        if (target==null){
            logg.info("USER REPO : User not existent, exiting the delete operation unsuccessfully");
            return null;
        }
        Connection c = dbutils.getConnection();
        try(PreparedStatement stmt = c.prepareStatement("DELETE FROM USER WHERE ID = " + integer )){
            stmt.execute();
            logg.info("Successfully deleted the user");
            return target;
        }catch (Exception e){
            logg.info(e.getMessage());
        }
        return null;
    }

    @Override
    public User update(User nou) {
        logg.info("USER REPO ; UPDATE FUNCTION ");
        try{
            valid.validate(nou);
        } catch (Exception e) {
           logg.info(e.getMessage());
        }
        User target = findOne(nou.getCodUser());
        if (target == null){
            logg.info("USER WITH ID " + nou.getCodUser() + " NOT EXISTING");
            save(nou);
            return null;
        }
        Connection c = dbutils.getConnection();
        try(PreparedStatement stmt = c.prepareStatement("UPDATE USER SET Nume = ?, Email = ? , Parola = ? WHERE ID = ?")){
            stmt.setString(1,nou.getNume());
            stmt.setString(2,nou.getEmail());
            stmt.setString(3,nou.getParola());
            stmt.setInt(4,nou.getCodUser());
            stmt.executeUpdate();
            return target;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findOne(Integer integer) {
        logg.info("USER REPO : Finding user with id " + integer);
        User u = null;
        Connection c = dbutils.getConnection();
        if (integer < 0){
            logg.error("ERORARE : ID INVALID");
            return u;
        }
        try(PreparedStatement stmt = c.prepareStatement("SELECT * FROM USER WHERE ID = ?")){
            stmt.setInt(1, integer);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = integer;
                String nume = rs.getString("Nume");
                String email = rs.getString("Email");
                String passwd = rs.getString("Parola");
                TipUser tip = TipUser.valueOf(rs.getString("Rol"));
                if (tip == TipUser.CLIENT){
                    u = new User(id, nume);
                }
                else u = new User(id, nume, email, passwd);
            }
        }catch (Exception e){
            logg.error(e.getMessage());
        }
        return u;
    }

    @Override
    public Collection<User> findAll() {
        logg.info("USER REPO : getting all entries from db ");
        Collection<User> users = new ArrayList<>();
        Connection conn = dbutils.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USER")){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("ID");
                String nume = rs.getString("Nume");
                String email = rs.getString("Email");
                String passwd = rs.getString("Parola");
                TipUser tip = TipUser.valueOf(rs.getString("Rol"));
                User u;
                if (tip == TipUser.CLIENT){
                    u = new User(id, nume);
                }
                else u = new User(id, nume, email, passwd);
                users.add(u);
            }
        } catch (Exception e) {
            logg.info(e.getMessage());
            e.printStackTrace();
        }
        logg.info("Here are the users");
        return users;
    }

    @Override
    public int size() {
        logg.info("USER REPO : repo size > > > ");
        Connection conn = dbutils.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("select count(*) as size from user")){
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    logg.info("USER REPO : exited user size");
                    return rs.getInt("size");
                }
            }
        } catch (Exception e) {
            logg.error(e.getMessage());
            e.printStackTrace();
        }
        logg.info("No entries in user table");
        return 0;
    }
}
