package Services;

import Model.Bilet;
import Model.ConcertDTO;
import Model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.List;

public interface IService {
    void Login(User u, IObserver obs) throws Exception;
    void Logout(User u, IObserver obs) throws Exception;
    void SellTicket(Bilet b) throws Exception;
    List<ConcertDTO> GetAllConcerts() throws Exception;
    List<ConcertDTO> GetConcertsByDate(LocalDate data) throws Exception;
}
