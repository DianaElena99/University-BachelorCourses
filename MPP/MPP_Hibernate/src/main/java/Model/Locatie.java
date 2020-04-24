package Model;

public class Locatie {
    private int id;

    public Locatie() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String nume;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    private int nrLocuri;

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public Locatie(int id, String nume, int nrLocuri) {
        this.id = id;
        this.nume = nume;
        this.nrLocuri = nrLocuri;
    }

    public Locatie(String nume, int nrLocuri) {
        this.nume = nume;
        this.nrLocuri = nrLocuri;
    }
}
