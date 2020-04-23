package Model;

import javax.persistence.Id;

public class Medicament {

    private int id;
    private String nume;
    private int cantitateDisp;
    private float pret;

    public Medicament(String nume, int cantit, float p){
        this.nume =nume;
        this.cantitateDisp = cantit;
        this.pret = p;
    }

    public Medicament(int id, String nume, int cantitateDisp, float pret) {
        this.id = id;
        this.nume = nume;
        this.cantitateDisp = cantitateDisp;
        this.pret = pret;
    }

    public Medicament() {

    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getCantitateDisp() {
        return cantitateDisp;
    }

    public void setCantitateDisp(int cantitateDisp) {
        this.cantitateDisp = cantitateDisp;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Medicament{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", cantitateDisp=" + cantitateDisp +
                ", pret=" + pret +
                '}';
    }
}
