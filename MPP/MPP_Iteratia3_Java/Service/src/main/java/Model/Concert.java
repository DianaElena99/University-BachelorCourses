package Model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Concert implements Serializable {
    private int ConID;
    private int ArtID;
    private int LocID;
    private int LocuriOcup;
    private LocalDateTime data;

    public Concert(int conID, int artID, int locID, int locuriOcup, LocalDateTime data) {
        ConID = conID;
        ArtID = artID;
        LocID = locID;
        LocuriOcup = locuriOcup;
        this.data = data;
    }

    public int getConID() {
        return ConID;
    }

    public void setConID(int conID) {
        ConID = conID;
    }

    public int getArtID() {
        return ArtID;
    }

    public void setArtID(int artID) {
        ArtID = artID;
    }

    public int getLocID() {
        return LocID;
    }

    public void setLocID(int locID) {
        LocID = locID;
    }

    public int getLocuriOcup() {
        return LocuriOcup;
    }

    public void setLocuriOcup(int locuriOcup) {
        LocuriOcup = locuriOcup;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
