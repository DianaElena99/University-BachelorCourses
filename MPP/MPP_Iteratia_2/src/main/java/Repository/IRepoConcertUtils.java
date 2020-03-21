package Repository;

import Model.Concert;
import Model.ConcertDTO;
import Model.Validators.NuMaiSuntLocuriException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IRepoConcertUtils<ID,E> extends IRepo<Integer, Concert> {
    List<ConcertDTO> findConcertsByDate(LocalDate d);
    void updateLocuriVandute(int idConcert, int nrLocuriNou) throws NuMaiSuntLocuriException;
    List<ConcertDTO> findConcertsDetails();
}
