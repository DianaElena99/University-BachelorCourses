import Model.Bilet;
import Model.Concert;
import Model.ConcertDTO;
import Model.User;
import Repository.BiletRepo;
import Repository.ConcertRepo;
import Repository.UserRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceImplemented implements IService {
    private UserRepo userRepo;
    private ConcertRepo concertRepo;
    private BiletRepo biletRepo;

    private final int defaultThreads = 5;
    public Map<User, IObserver> loggedClients;

    public ServiceImplemented(UserRepo ur, BiletRepo br, ConcertRepo cr){
        userRepo = ur;
        concertRepo = cr;
        biletRepo = br;
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(User u, IObserver obs) throws Exception {
        int valid = userRepo.autentificare(u.getNume(), u.getParola());
        if (valid == -1){
            throw new Exception("Invalid username or passwd");
        }
        else{
            if(!loggedClients.containsKey(u)){
                loggedClients.put(u, obs);
            }
        }
    }

    @Override
    public synchronized void logout(User u, IObserver obs) throws Exception {
        IObserver localClient = loggedClients.remove(u);
        if(localClient == null){
            throw new Exception("User already logged out");
        }
    }

    @Override
    public synchronized void sellTicket(Bilet b) {
        biletRepo.saveBilet(b);
        int nrNouLocuri = b.getNrLocuri() + concertRepo.findOne(b.getCodConcert()).getLocuriOcup();
        concertRepo.updateLocuriVandute(b.getCodConcert(), nrNouLocuri);
        List<ConcertDTO> li = concertRepo.findConcertsDetails();
        broadcastTable(li);
        //return li;
    }

    @Override
    public synchronized List<ConcertDTO> searchConcerts(LocalDate d) {
        List<ConcertDTO> concerts = concertRepo.findConcertsByDate(d);
        return concerts;
    }

    public synchronized List<ConcertDTO> getAllConcerts(){
        return concertRepo.findConcertsDetails();
    }

    public synchronized void broadcastTable(List<ConcertDTO> l) {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreads);
        for(IObserver obs : loggedClients.values()){
            if(obs!=null ){
                executor.execute(()->{
                    try{
                        System.out.println("Notifying : " + obs);
                        obs.update(l);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        }
        executor.shutdown();

    }
}
