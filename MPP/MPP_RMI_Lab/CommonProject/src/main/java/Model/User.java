package Model;

import java.io.Serializable;

public class User implements Serializable {
    private String nume;
    private String parola;


    public User(String nume, String parola){
        this.nume = nume;
        this.parola = parola;
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

    @Override
    public String toString() {
        return "User{" +
                "nume='" + nume + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
