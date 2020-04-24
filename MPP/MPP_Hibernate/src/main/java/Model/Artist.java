package Model;

import java.io.Serializable;

public class Artist implements Serializable {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String nume;

    public Artist(int ID, String nume) {
        this.id = ID;
        this.nume = nume;
    }

    public Artist() {

    }

    public Artist(String nume){
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public int getID() {
        return id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setID(int ID) {
        this.id = ID;
    }


    @Override
    public String toString() {
        return "ID : " + id + ", Nume : " + nume;
    }
}
