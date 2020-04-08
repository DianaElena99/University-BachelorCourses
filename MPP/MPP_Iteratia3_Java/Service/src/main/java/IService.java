
import Model.Bilet;
import Model.ConcertDTO;

import java.time.LocalDate;
import java.util.List;

public interface IService {
    void login(Model.User u, IObserver obs) throws Exception;
    void logout(Model.User u, IObserver obs) throws Exception;
    void sellTicket(Bilet b);
    List<ConcertDTO> searchConcerts(LocalDate d);
    List<ConcertDTO> getAllConcerts();
    //void broadcastTable(List<ConcertDTO> l) throws Exception;
}
