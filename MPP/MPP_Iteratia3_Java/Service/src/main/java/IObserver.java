import Model.Bilet;
import Model.ConcertDTO;
import Model.User;
import jdk.vm.ci.meta.Local;

import java.time.LocalDate;
import java.util.List;

public interface IObserver {
       void update(List<ConcertDTO> l);
}
