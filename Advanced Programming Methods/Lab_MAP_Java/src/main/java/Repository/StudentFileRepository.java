package Repository;

import Domain.Student;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;

import java.io.*;
import java.util.Collection;

public class StudentFileRepository extends AbstractRepository<Student, Integer> {
    private String fileName;
    
    public StudentFileRepository(Validator<Student> valid, String fileName){
        super(valid);
        this.fileName = fileName;
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){
            String line;
            while(!(line = bufferedReader.readLine()).equals("")){
                String[] words = line.split(";");
                if (words.length != 4)
                    break;
                Integer idStudent = Integer.parseInt(words[0]);
                Integer grupa = Integer.parseInt(words[1]);
                String nume = words[2];
                String email = words[3];
                Student stud = new Student(idStudent, grupa, nume, email);
                super.save(stud);
            }
        } catch (NullPointerException | IOException | IllegalArgumentException | ValidatorException e) {
            System.out.println("");
        }
    }

    @Override
    public Student save(Student obj) throws IllegalArgumentException, ValidatorException {
        Student stud =  super.save(obj);
        if (stud==null)
            writeToFile();
        return stud;
    }

    @Override
    public Student delete(Integer integer) throws IllegalArgumentException {
        Student target = super.delete(integer);
        if (target != null)
            writeToFile();
        return target;
    }

    @Override
    public Student update(Student obj) throws ValidatorException, IllegalArgumentException {
        Student target = super.update(obj);
        if (target == null)
            writeToFile();
        return target;
    }

    @Override
    public Student find(Integer integer) throws IllegalArgumentException {
        return super.find(integer);
    }

    @Override
    public Collection<Student> findAll() {
        return super.findAll();
    }

    private void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Student stud : super.findAll()){
                String id = stud.getID().toString();
                String grupa = stud.getGroup().toString();
                String nume = stud.getName();
                String email = stud.getEmail();
                String result = id + ";" + grupa + ";" + nume + ";" + email + "\n";
                bufferedWriter.write(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
