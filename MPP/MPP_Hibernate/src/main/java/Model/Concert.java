package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Concert {

    private int id;
    private int idArtist;
    private int idLocatie;
    private String data;
    private int nrLocuri;

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIdLocatie() {
        return idLocatie;
    }

    public void setIdLocatie(int idLocatie) {
        this.idLocatie = idLocatie;
    }

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Concert(int id, int idArtist, int idLocatie, String data, int nrLocuri) {
        this.id = id;
        this.idArtist = idArtist;
        this.idLocatie = idLocatie;
        this.data = data;
        this.nrLocuri = nrLocuri;
    }

    public Concert(int idArtist, int idLocatie, String data, int nrLocuri) {
        this.idArtist = idArtist;
        this.idLocatie = idLocatie;
        this.data = data;
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String toString() {
        return "Concert{" +
                "id=" + id +
                ", idArtist=" + idArtist +
                ", idLocatie=" + idLocatie +
                ", data='" + data + '\'' +
                ", nrLocuri=" + nrLocuri +
                '}';
    }

    public Concert() {
    }
}
