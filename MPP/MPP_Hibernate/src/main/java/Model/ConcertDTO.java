package Model;

import javax.persistence.*;
import java.time.LocalDateTime;

public class ConcertDTO {

    private int id;

    private String artist;

    private String location;

    private String data;

    private Integer locuriOcup;

    private Integer locuriFree;

    private Integer locuriTotal;

    public ConcertDTO(int id, String artistName, String locationName, String data,
                      Integer locuriOcup, Integer locuriTotal) {
        this.id = id;
        this.artist = artistName;
        this.location = locationName;
        this.data = data;
        this.locuriOcup = locuriOcup;
        this.locuriTotal = locuriTotal;
        this.locuriFree = locuriTotal - locuriOcup;
    }

    public ConcertDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getLocuriOcup() {
        return locuriOcup;
    }

    public void setLocuriOcup(Integer locuriOcup) {
        this.locuriOcup = locuriOcup;
    }

    public Integer getLocuriFree() {
        return locuriFree;
    }

    public void setLocuriFree(Integer locuriFree) {
        this.locuriFree = locuriFree;
    }

    public Integer getLocuriTotal() {
        return locuriTotal;
    }

    public void setLocuriTotal(Integer locuriTotal) {
        this.locuriTotal = locuriTotal;
    }

    @Override
    public String toString() {
        return "ConcertDTO{" +
                "id=" + id +
                ", artist='" + artist + '\'' +
                ", location='" + location + '\'' +
                ", data='" + data + '\'' +
                ", locuriOcup=" + locuriOcup +
                ", locuriFree=" + locuriFree +
                ", locuriTotal=" + locuriTotal +
                '}';
    }
}
