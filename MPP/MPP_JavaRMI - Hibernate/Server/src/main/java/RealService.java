import Model.Bilet;
import Model.Concert;
import Model.ConcertDTO;
import Model.User;
import Services.IObserver;
import Services.IService;
import repo.BiletRepo;
import repo.BiletRepoHibernate;
import repo.ConcertRepo;
import repo.UserRepo;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RealService implements IService {
    public BiletRepoHibernate biletRepo;
    public ConcertRepo concertRepo;
    public UserRepo userRepo;
    public Map<User, IObserver> loggedClients;

    private final int defaultThreadCount = 5;

    public RealService(BiletRepoHibernate br, ConcertRepo cr, UserRepo ur){
        biletRepo = br;
        concertRepo = cr;
        userRepo = ur;
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void Login(User u, IObserver obs) throws Exception {
        System.out.println("Logging in user : " + u + " obs:" + obs) ;
        int valid = userRepo.autentificare(u.getNume(), u.getParola());
        if (valid == -1){
            throw new Exception("Invalid username or passwd");
        }
        loggedClients.put(u, obs);
        System.out.println(loggedClients);
    }

    @Override
    public synchronized void Logout(User u, IObserver obs)  throws Exception  {
        IObserver localClient = loggedClients.remove(u);
        if(localClient == null){
            throw new Exception("User already logged out");
        }
    }

    @Override
    public synchronized void SellTicket(Bilet b) throws Exception  {
        biletRepo.save(b);
        int nrNouLocuri = b.getNrLocuri() + concertRepo.findOne(b.getCodConcert()).getLocuriOcup();
        concertRepo.updateLocuriVandute(b.getCodConcert(), nrNouLocuri);
        List<ConcertDTO> li = concertRepo.findConcertsDetails();
        notifyObservers(li);
    }

    @Override
    public synchronized List<ConcertDTO> GetAllConcerts()  throws Exception  {
        return concertRepo.findConcertsDetails();
    }

    @Override
    public synchronized List<ConcertDTO> GetConcertsByDate(LocalDate data)  throws Exception  {
        return concertRepo.findConcertsByDate(data);
    }

    private void notifyObservers(List<ConcertDTO> nou){
        System.out.println(nou);
        System.out.println("Notifying all the clients");
        System.out.println(loggedClients.values());
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadCount);
        for(IObserver obs : loggedClients.values()){
            if (obs!=null){
                executor.execute(()->{
                    try{
                        System.out.println("Notifying : " + obs);
                        obs.update(nou);
                    }catch (Exception e){
                        System.out.println("Something went wrong : " + e.getMessage());
                    }
                });
            }
        }
        executor.shutdown();
    }
}
