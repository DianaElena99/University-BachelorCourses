package Repository;

import Domain.Homework;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PostgreSQLHomeworkRepository extends AbstractRepository<Homework,Integer> {
    public PostgreSQLHomeworkRepository(Validator<Homework> v){
        super(v);
        Connection c = null;
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","DDDddd111!!!");
            loadFromDatabase(c);
            c.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public Homework save(Homework st) throws ValidatorException, IllegalArgumentException {
        Homework result = super.save(st);
        if (result == null){
            String insert = "INSERT INTO TEMA(ID,START, DEADLINE, TITLU)" +
                    "VALUES ( " + st.getID() + ", " + st.getWeekRecv() + ", "
                     + st.getDeadline()  + ", " + "'" + st.getTitle()+ "'" + ");";
            executeSQL(insert);
        }
        return result;
    }

    public Homework delete(Integer id) throws IllegalArgumentException {
        Homework target = super.delete(id);
        String remove="";
        if (target!=null){
            remove = "DELETE FROM TEMA WHERE ID = " + id + " ;";
            executeSQL(remove);
        }
        return target;
    }

    public Homework update(Homework st) throws ValidatorException, IllegalArgumentException {
        Homework target = super.update(st);
        String modify = "UPDATE TEMA set TITLU = '" + st.getTitle() +
                "', START = " + st.getWeekRecv() + ", DEADLINE = " + st.getDeadline() +
                "WHERE ID = " + st.getID();
        executeSQL(modify);
        return target;
    }

    public void loadFromDatabase(Connection c){
        Collection<Homework> teme = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String selectAll = "SELECT * FROM Tema";

            ResultSet rs = null;
            rs = stmt.executeQuery(selectAll);
            while (rs.next()){
                Homework hw = new Homework(rs.getInt(1), rs.getString(4), rs.getInt(2),rs.getInt(3));
                teme.add(hw);
                super.save(hw);
            }
        } catch (SQLException | IllegalArgumentException | ValidatorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Homework> findAll(){
        return super.findAll();
    }

    private void selectAll(){
        Connection c = null;
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","DDDddd111!!!");
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM TEMA");
            while(rs.next()){
                System.out.println(rs.getInt(1) + " " + rs.getString(4));
            }
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeSQL(String comanda)  {
        Connection c = null;
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","DDDddd111!!!");
            c.setAutoCommit(false);

            Statement stmt = c.createStatement();
            stmt.executeUpdate(comanda);
            c.commit();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
