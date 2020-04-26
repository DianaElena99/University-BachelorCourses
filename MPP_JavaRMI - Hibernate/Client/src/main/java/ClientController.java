import Model.Bilet;
import Model.ConcertDTO;
import Model.User;
import Services.IObserver;
import Services.IService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientController extends UnicastRemoteObject implements IObserver, Serializable {

    public IService server;
    public User usr;
    public ObservableList<ConcertDTO> concerts;

    public ClientController(IService serv) throws RemoteException {
        server = serv;
        concerts = FXCollections.observableArrayList();
    }

    @Override
    public void update(List<ConcertDTO> actual)  throws RemoteException{
        concerts.setAll(actual);
    }

    public void LoginCtrl(User u) {
        try{
            server.Login(u, this);
            usr = u;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LogoutCtrl(User u){
        try{
            server.Logout(u, this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<ConcertDTO> GetConcertsByDate(LocalDate ld) {
        try{
            return server.GetConcertsByDate(ld);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ConcertDTO> GetAllConcerts() {
        try {
            concerts.setAll(server.GetAllConcerts());
            return concerts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void SellTicket(Bilet b) {
        try{
            server.SellTicket(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
