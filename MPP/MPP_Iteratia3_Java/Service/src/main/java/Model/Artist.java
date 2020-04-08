package Model;

public class Artist {
    private int ID;
    private String nume;

    public Artist(int ID, String nume) {
        this.ID = ID;
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public int getID() {
        return ID;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;
        Artist aux = (Artist)obj;
        return aux.ID == this.ID;
    }

    @Override
    public String toString() {
        return "ID : " + ID + ", Nume : " + nume;
    }
}
