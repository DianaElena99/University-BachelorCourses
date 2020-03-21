package Repository;

import Domain.Grade;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class PostgreSQLGradeRepository extends AbstractRepository<Grade,String> {
    public PostgreSQLGradeRepository(Validator<Grade>v){
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

    public Grade save(Grade gr) throws ValidatorException, IllegalArgumentException {
        Grade saved = super.save(gr);
        if (saved == null){
            String insert = "INSERT INTO NOTE(ID_STUDENT, ID_TEMA, NOTA, PROF, DATA)" +
                    "VALUES (" + gr.getID_Student() + ", " + gr.getID_Tema() + ", " +
                    gr.getNota() + ", '" + gr.getProfesor() + "' , '" + gr.getData().toString() + "');";
            executeSQL(insert);
        }
        return saved;
    }

    public Grade delete(String id) throws IllegalArgumentException {
        Grade target = super.delete(id);
        String cmd = "";
        if (target!=null)
            cmd = "DELETE FROM NOTE WHERE ID_STUDENT = " + target.getID_Student() + "AND ID_TEMA " + target.getID_Tema() +" ;";
        return target;
    }

    public Grade update(Grade gr) throws ValidatorException, IllegalArgumentException {
        Grade modified = super.update(gr);
        String cmd1="";
        if (modified!=null){
             cmd1 = "delete from Note where ID_STUDENT = " + gr.getID_Student()
                + " AND ID_TEMA = " + gr.getID_Tema()+" ;";
            save(gr);
        }
        return modified;
    }

    private void executeSQL(String comanda) {
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

    private void loadFromDatabase(Connection c) {
        Collection<Grade> note = new ArrayList<>();
        try{
            Statement stmt = c.createStatement();
            String selectAll = "SELECT * FROM NOTE";
            ResultSet rs = stmt.executeQuery(selectAll);
            while(rs.next()){
                LocalDate data = LocalDate.parse(rs.getString(5));
                Grade gr = new Grade(rs.getInt(1), rs.getInt(2), rs.getFloat(3), data, rs.getString(4));
                super.save(gr);
                note.add(gr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
