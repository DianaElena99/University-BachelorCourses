package Model;

public class Bilet {
    private int codBilet;
    private String numeClient;
    private int nrLocuri;
    private int codConcert;

    public Bilet(int codBilet, String numeClient, int codConcert, int nrLocuri) {
        this.codBilet = codBilet;
        this.numeClient = numeClient;
        this.codConcert = codConcert;
        this.nrLocuri = nrLocuri;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public int getCodBilet() {
        return codBilet;
    }

    public void setCodBilet(int codBilet) {
        this.codBilet = codBilet;
    }

   public int getCodConcert() {
        return codConcert;
    }

    public void setCodConcert(int codConcert) {
        this.codConcert = codConcert;
    }

}
