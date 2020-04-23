package Model;

public class MedicamentDTO {
    public int idComanda;
    public String email;
    public String numeMedicament;
    public int cantitComandata;
    public float pretBucata;
    private float pretTot;

    public MedicamentDTO(){}

    public MedicamentDTO(int idComanda, String email, String numeMedicament, int cantitComandata, float pretBucata) {
        this.idComanda = idComanda;
        this.email = email;
        this.numeMedicament = numeMedicament;
        this.cantitComandata = cantitComandata;
        this.pretBucata = pretBucata;
        this.pretTot = pretBucata * cantitComandata;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeMedicament() {
        return numeMedicament;
    }

    public void setNumeMedicament(String numeMedicament) {
        this.numeMedicament = numeMedicament;
    }

    public int getCantitComandata() {
        return cantitComandata;
    }

    public void setCantitComandata(int cantitComandata) {
        this.cantitComandata = cantitComandata;
    }

    public float getPretBucata() {
        return pretBucata;
    }

    public void setPretBucata(float pretBucata) {
        this.pretBucata = pretBucata;
    }

    @Override
    public String toString() {
        return "MedicamentDTO{" +
                "idComanda=" + idComanda +
                ", email='" + email + '\'' +
                ", numeMedicament='" + numeMedicament + '\'' +
                ", cantitComandata=" + cantitComandata +
                ", pretBucata=" + pretBucata +
                '}';
    }

    public float getPretTot() {
        return pretTot;
    }

    public void setPretTot(float pretTot) {
        this.pretTot = pretTot;
    }
}
