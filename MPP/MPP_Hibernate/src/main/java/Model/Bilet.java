package Model;

public class Bilet {

    private int id;
    private String client;
    private int idConcert;
    private int nrLocuri;

    public Bilet(int id, String client, int idConcert, int nrLocuri) {
        this.id = id;
        this.client = client;
        this.idConcert = idConcert;
        this.nrLocuri = nrLocuri;
    }

    public Bilet(String client, int idConcert, int nrLocuri) {
        this.client = client;
        this.idConcert = idConcert;
        this.nrLocuri = nrLocuri;
    }

    public Bilet() {
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public int getIdConcert() {
        return idConcert;
    }

    public void setIdConcert(int idConcert) {
        this.idConcert = idConcert;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
