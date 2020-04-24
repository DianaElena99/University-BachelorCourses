package Repository.hibernate_repos;

import Model.ConcertDTO;
import Model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConcertDTORepo {

    private DBUtils db;

    public ConcertDTORepo(){
        db = new DBUtils();
    }

    public List<ConcertDTO> findConcertsByDate(String date){
        return findConcertsDetails().stream().filter(x->x.getData().contains(date)).collect(Collectors.toList());
    }

    public List<ConcertDTO> findConcertsDetails() {
        List<ConcertDTO> rezFin = new ArrayList<>();
        Connection con = db.getConnection();
        String comanda = "select c.id, a.Nume as ARTIST , L.Nume as LOCATIE, c.Data, c.NrLocuri as LOCURI_OCUP, L.NrLocuri as NrLocuriTot from Concert c inner join Locatie L on L.ID = c.idLocatie inner join Artist a on a.ID = c.idArtist ";

        try(PreparedStatement ps = con.prepareStatement(comanda)){
            ResultSet r = ps.executeQuery();
            while(r.next()){
                int idc = r.getInt(1);
                String numea = r.getString(2);
                String numel = r.getString(3);
                String auxData = r.getString(4);
                int nrLocuriO = r.getInt(5);
                int nrLocuriTot = r.getInt(6);

                ConcertDTO rez = new ConcertDTO(idc, numea, numel, auxData, nrLocuriO, nrLocuriTot);
                rezFin.add(rez);
            } }catch (SQLException e) {
                e.printStackTrace();
        }
        return rezFin ;
    }
}
