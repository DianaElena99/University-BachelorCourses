package Service;

import Model.Bilet;
import Model.Concert;
import Model.ConcertDTO;
import Model.Locatie;
import Model.Validators.NuMaiSuntLocuriException;
import Repository.*;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private ConcertRepo concertRepo;
    private BiletRepo biletRepo;
    private UserRepo userRepo;
    public static int countBilete = 0;

    public Service(UserRepo ur, ConcertRepo cr, BiletRepo br){
        this.concertRepo = cr;
        this.biletRepo = br;
        this.userRepo = ur;
        activeUsers = new ArrayList<>();
        countBilete = biletRepo.size() ;
    }

    public void VindeBilete(int nrBilete, String numeClient, int idConcert){
        countBilete++;
        Bilet b = new Bilet(countBilete, numeClient, idConcert, nrBilete);
        int nrNou = nrBilete + concertRepo.findOne(idConcert).getLocuriOcup();
        biletRepo.saveBilet(b);
        try {
            concertRepo.updateLocuriVandute(idConcert, nrNou);
        } catch (NuMaiSuntLocuriException e) {
            e.printStackTrace();
        }
    }

    public List<ConcertDTO> GetConcerte() {
        List<ConcertDTO> lista = concertRepo.findConcertsDetails();
        return lista;
    }

    public List<ConcertDTO> ConcertsByDate(LocalDate data) {
        List<ConcertDTO> rez = concertRepo.findConcertsByDate(data);
        return rez;
    }

    public List<Integer> activeUsers;

    public int LogIn(String usr, String pass) {
        int id = userRepo.autentificare(usr, pass);
        if (id < 0){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("User sau parola gresite");
            a.show();
        }else{
            activeUsers.add(id);
        }
        return id;
    }

    public void LogOut(int u) {
        System.out.println(activeUsers);
        activeUsers.remove((Object)u);
    }
}
