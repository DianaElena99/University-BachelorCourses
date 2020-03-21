package Domain;

import java.time.LocalDate;

public class Grade implements Entity<String>{
    private Float Nota;
    private LocalDate data;
    private String profesor;
    private String ID;
    private Integer ID_Student;
    private Integer ID_Tema;

    public Grade (Integer ID_Student, Integer ID_Tema, Float Nota, LocalDate data, String profesor) {
        this.Nota = Nota;
        this.data = data;
        this.profesor = profesor;
        this.ID_Student = ID_Student;
        this.ID_Tema = ID_Tema;
        this.ID = ID_Student.toString() + '_' +ID_Tema.toString();
    }

    public Integer getID_Student() {
        return ID_Student;
    }

    public Integer getID_Tema() {
        return ID_Tema;
    }

    public void setID_Student(Integer ID_Student) {
        this.ID_Student = ID_Student;
    }

    public void setID_Tema(Integer ID_Tema) {
        this.ID_Tema = ID_Tema;
    }

    public Float getNota() {
        return Nota;
    }

    public void setNota(Float nota) {
        Nota = nota;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public void setID(String s) {
        this.ID = s;
    }

    @Override
    public String toString() {
        return "Grade{ID=" + this.getID() +
                ", Nota=" + this.Nota +
                ", Profesor=" + this.getProfesor() +
                ", Data = " + this.getData() + "}";
    }
}
