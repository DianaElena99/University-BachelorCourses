import Model.Comanda;
import Model.Medicament;
import Repository.ComandaRepo;
import Repository.MedicamentRepo;
import Repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        UserRepo ur = new UserRepo();
        MedicamentRepo mr = new MedicamentRepo();
        ComandaRepo cr = new ComandaRepo();
        List<Medicament> med = new ArrayList<>();
        /*med.add(mr.getAll().get(0));
        Comanda comanda = new Comanda(3,"drion@gmail.com","ENDOCRINOLOGIE",med.get(0).getPret());
        comanda.setMedicamentList(med);
        //cr.addComanda(comanda);
        cr.removeComanda(comanda);
        System.out.println(cr.getAll());
*/
        System.out.println(ur.findUser("drion@gmail.com"));
        System.out.println(ur.getUsersBySection("cardiologie"));
    }
}
