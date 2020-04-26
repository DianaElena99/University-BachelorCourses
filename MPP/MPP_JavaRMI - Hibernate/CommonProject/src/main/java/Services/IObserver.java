package Services;

import Model.ConcertDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IObserver extends Remote {
    void update(List<ConcertDTO> actual) throws RemoteException;
}
