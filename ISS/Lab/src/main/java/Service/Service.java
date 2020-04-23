package Service;

import Model.Comanda;
import Model.Medicament;
import Model.MedicamentDTO;
import Model.User;
import Repository.ComandaRepo;
import Repository.MedicamentRepo;
import Repository.UserRepo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service implements Observable {
    public List<Observer> activeUsers;
    public UserRepo userRepo;
    public MedicamentRepo medicamentRepo;
    public ComandaRepo comandaRepo;

    public Service(UserRepo ur, MedicamentRepo mr, ComandaRepo cr){
        userRepo = ur;
        medicamentRepo = mr;
        comandaRepo = cr;
        activeUsers = new ArrayList<Observer>();
    }

    public User Login(String e, String p) {
        User u = userRepo.checkCredentials(e,p);
        return u;
    }

    @Override
    public void addObserver(Observer o) {
        activeUsers.add(o);
    }

    @Override
    public void notifyObservers(TipEvent te) {
        activeUsers.forEach(x -> x.update(te));
    }

    public void modificaCantitate(Medicament m, String source) {
        medicamentRepo.updateMedicament(m.getId(), m.getCantitateDisp());
        notifyObservers(TipEvent.MODIF_CANTITATE);
    }

    public void createUser(User user) {
        try{
            userRepo.addNewUser(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addComanda(Comanda cmd) {
        try{
            comandaRepo.addComanda(cmd);
            notifyObservers(TipEvent.ADD_COMANDA);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onoreazaComanda(Comanda cmd) {
        try{
            comandaRepo.removeComanda(cmd);
            notifyObservers(TipEvent.REMOVE_COMANDA);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void anuleazaComanda(Comanda cd) {
        try{
            comandaRepo.removeComanda(cd);
            notifyObservers(TipEvent.ANULARE_COMANDA);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
