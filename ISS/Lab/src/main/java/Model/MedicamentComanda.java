package Model;

import com.sun.tools.javac.util.Pair;

import java.io.Serializable;

public class MedicamentComanda implements Serializable {
    private int idComanda;
    private int idMedicament;
    private int cantitateComandata;

    public MedicamentComanda(){}

    public MedicamentComanda( int idComanda, int idMedicament, int cantitateComandata) {
        this.idComanda = idComanda;
        this.idMedicament = idMedicament;
        this.cantitateComandata = cantitateComandata;
    }


    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    public int getCantitateComandata() {
        return cantitateComandata;
    }

    public void setCantitateComandata(int cantitateComandata) {
        this.cantitateComandata = cantitateComandata;
    }
}
