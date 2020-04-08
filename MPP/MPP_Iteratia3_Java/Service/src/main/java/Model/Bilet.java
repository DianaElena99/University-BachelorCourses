package Model;

import java.io.Serializable;

public class Bilet implements Serializable {
    private String numeClient;
    private int nrLocuri;
    private int codConcert;

    public Bilet( String numeClient, int codConcert, int nrLocuri) {
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

   public int getCodConcert() {
        return codConcert;
    }

    public void setCodConcert(int codConcert) {
        this.codConcert = codConcert;
    }

}
