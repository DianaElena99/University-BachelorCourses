package Model;

import java.time.LocalDateTime;

public class ConcertDTO {
    private int ConcertID;
    private String ArtistName;
    private String LocationName;
    private LocalDateTime Data;
    private Integer LocuriOcup;
    private Integer LocuriFree;
    private Integer LocuriTotal;

    public ConcertDTO(int concertID, String artistName, String locationName, LocalDateTime data,
                      Integer locuriOcup, Integer locuriTotal) {
        ConcertID = concertID;
        ArtistName = artistName;
        LocationName = locationName;
        Data = data;
        LocuriOcup = locuriOcup;
        LocuriTotal = locuriTotal;
        LocuriFree = locuriTotal - locuriOcup;
    }

    public int getConcertID() {
        return ConcertID;
    }

    public void setConcertID(int concertID) {
        ConcertID = concertID;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public LocalDateTime getData() {
        return Data;
    }

    public void setData(LocalDateTime data) {
        Data = data;
    }

    public Integer getLocuriOcup() {
        return LocuriOcup;
    }

    public void setLocuriOcup(Integer locuriOcup) {
        LocuriOcup = locuriOcup;
    }

    public Integer getLocuriFree() {
        return LocuriFree;
    }

    public void setLocuriFree(Integer locuriFree) {
        LocuriFree = locuriFree;
    }

    public Integer getLocuriTotal() {
        return LocuriTotal;
    }

    public void setLocuriTotal(Integer locuriTotal) {
        LocuriTotal = locuriTotal;
    }

    @Override
    public String toString() {
        return "ConcertDTO{" +
                "ConcertID=" + ConcertID +
                ", ArtistName='" + ArtistName + '\'' +
                ", LocationName='" + LocationName + '\'' +
                ", Data=" + Data +
                ", LocuriOcup=" + LocuriOcup +
                ", LocuriFree=" + LocuriFree +
                ", LocuriTotal=" + LocuriTotal +
                '}';
    }
}
