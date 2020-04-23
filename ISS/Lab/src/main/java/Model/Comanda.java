package Model;

import java.io.Serializable;
import java.util.List;

public class Comanda implements Serializable {
    private int cod;
    private String email;
    private String sectie;
    private float subtotal;
    private List<Medicament> medicamentList;

    public Comanda() {
    }

    public Comanda(int cod, String email, String sectie, float subtotal) {
        this.cod = cod;
        this.email = email;
        this.sectie = sectie;
        this.subtotal = subtotal;
    }

    public List<Medicament> getMedicamentList() {
        return medicamentList;
    }

    public void setMedicamentList(List<Medicament> medicamentList) {
        this.medicamentList = medicamentList;
    }

    public Comanda(String email, String section, Float subtotalComandaCurenta) {
        this.email = email;
        this.sectie = section;
        this.subtotal = subtotalComandaCurenta;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public String getSectie() {
        return sectie;
    }

    public void setSectie(String sectie) {
        this.sectie = sectie;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
