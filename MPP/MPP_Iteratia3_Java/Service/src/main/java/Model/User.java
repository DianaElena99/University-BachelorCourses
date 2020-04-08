package Model;

import java.io.Serializable;

public class User implements Serializable {
    private String nume;
    private String parola;
    private String email;
    private Integer codUser;

    public User(int cod, String nume, String email, String parola) {
        this.nume = nume;
        this.parola = parola;
        this.codUser = cod;
        this.email = email;
    }

    public User(String user, String parola){
        this.nume = user;
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setCodUser(Integer codUser) {
        this.codUser = codUser;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public Integer getCodUser() {
        return codUser;
    }

    public void setCodUser(int codUser) {
        this.codUser = codUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "nume='" + nume + '\'' +
                ", parola='" + parola + '\'' +
                ", email='" + email + '\'' +
                ", codUser=" + codUser +
                '}';
    }
}
