package Repository;

import Domain.Student;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class PostrgeSQLStudentRepository extends AbstractRepository<Student, Integer> {
    public PostrgeSQLStudentRepository(Validator<Student> v) {
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

    public Student save(Student st) throws ValidatorException, IllegalArgumentException {
        Student result = super.save(st);
        if (result == null){
            String insert = "INSERT INTO STUDENTS(ID, GRUPA, NUME, EMAIL)" +
                    "VALUES ( " + st.getID() + ", " + st.getGroup() + ", "
                    + "'" + st.getName() + "'" + ", " + "'" + st.getEmail()+ "'" + ");";
            executeSQL(insert);
        }
        return result;
    }

    public Student delete(Integer id) throws IllegalArgumentException {
        Student target = super.delete(id);
        String remove="";
        if (target!=null){
            remove = "DELETE FROM STUDENTS WHERE ID = " + id + " ;";
            executeSQL(remove);
        }
        return target;
    }

    public Student update(Student st) throws ValidatorException, IllegalArgumentException {
        Student target = super.update(st);
        String modify = "UPDATE STUDENTS set GRUPA = " + st.getGroup() +
                ", NUME = '" + st.getName() + "', EMAIL = '" + st.getEmail() +
                "' WHERE ID = " + st.getID();
        executeSQL(modify);
        return target;
    }

    public void loadFromDatabase(Connection c){
        Collection<Student> students = new ArrayList<>();
        try {
            Statement stmt = c.createStatement();
            String selectAll = "SELECT * FROM Students";

            ResultSet rs = null;
            rs = stmt.executeQuery(selectAll);
            while (rs.next()){
                Student st = new Student(rs.getInt(1), rs.getInt(2), rs.getString(3),rs.getString(4));
                students.add(st);
                super.save(st);
                //System.out.println(rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getString(3) + " "+ rs.getString(4));
            }
        } catch (SQLException | IllegalArgumentException | ValidatorException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Student> findAll(){
        return super.findAll();
    }
}
