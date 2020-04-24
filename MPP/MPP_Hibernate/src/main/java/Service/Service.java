package Service;

import Model.Bilet;
import Model.ConcertDTO;
import Model.User;
import Repository.hibernate_repos.BiletHibernateRepo;
import Repository.hibernate_repos.ConcertDTORepo;
import Repository.hibernate_repos.ConcertHibernateRepo;
import Repository.hibernate_repos.UserHibernateRepo;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service {
        private ConcertHibernateRepo concertRepo;
        private ConcertDTORepo dtoRepo;
        private BiletHibernateRepo biletRepo;
        private UserHibernateRepo userRepo;

        public Service(UserHibernateRepo ur, ConcertHibernateRepo cr, ConcertDTORepo cdto, BiletHibernateRepo br){
            this.concertRepo = cr;
            this.biletRepo = br;
            this.userRepo = ur;
            this.dtoRepo = cdto;
            activeUsers = new ArrayList<>();
        }

        public void VindeBilete(int nrBilete, String numeClient, int idConcert){
            Bilet b = new Bilet(numeClient, idConcert, nrBilete);
            int nrNou = nrBilete + concertRepo.findOne(idConcert).getNrLocuri();
            biletRepo.save(b);
            try {
                concertRepo.updateLocuriLibere(idConcert, nrNou);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public List<ConcertDTO> GetConcerte() {
            List<ConcertDTO> lista = dtoRepo.findConcertsDetails();
            return lista;
        }

        public List<ConcertDTO> ConcertsByDate(LocalDate data) {
            List<ConcertDTO> rez = dtoRepo.findConcertsByDate(data.toString());
            return rez;
        }

        public List<String> activeUsers;

        public int LogIn(User u) {
            int id = userRepo.checkCredentials(u);
            if (id < 0){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("User sau parola gresite");
                a.show();
            }else{
                activeUsers.add(u.getEmail());
            }
            return id;
        }

        public void LogOut(String u) {
            System.out.println(activeUsers);
            activeUsers.remove((Object)u);
            return;
        }
    }
