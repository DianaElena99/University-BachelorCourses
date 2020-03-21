package Model;

public class Locatie {
    private int ID;
    private String Nume;
    private int Locuri;

    public Locatie(int ID, String nume, int locuri) {
        this.ID = ID;
        Nume = nume;
        Locuri = locuri;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNume() {
        return Nume;
    }

    public void setNume(String nume) {
        Nume = nume;
    }

    public int getLocuri() {
        return Locuri;
    }

    public void setLocuri(int locuri) {
        Locuri = locuri;
    }
}
