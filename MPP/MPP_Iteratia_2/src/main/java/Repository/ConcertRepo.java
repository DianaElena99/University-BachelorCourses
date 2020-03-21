package Repository;

import Model.Concert;
import Model.ConcertDTO;
import Model.Validators.NuMaiSuntLocuriException;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


public class ConcertRepo implements IRepoConcertUtils<Integer, Concert> {
    private static final Logger logg = LogManager.getLogger("LoggerConcerRepo");
    private DBUtils db;

    public ConcertRepo(){
        db = new DBUtils();
    }

    @Override
    public Concert findOne(Integer integer) {
        logg.info("Entered function findOne > ConcertRepo");
        if (integer < 0){
            logg.info("INVALID ID !!! EXITING FUNCTION");
            return null;
        }
        Concert target;
        Connection c = db.getConnection();
        try(PreparedStatement ps = c.prepareStatement("SELECT * FROM CONCERT WHERE ID = ?")){
            ps.setInt(1,integer);
            ResultSet r = ps.executeQuery();
            while(r.next()){
                int idArtist = r.getInt(2);
                int idLocatie = r.getInt(3);
                int nrLocuri = r.getInt(4);
                String data_aux = r.getString("Data");
                LocalDateTime d = LocalDateTime.parse(data_aux, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                Concert con = new Concert(integer, idArtist, idLocatie, nrLocuri, d);
                logg.info("Found the concert with id " + integer);
                return con;
            }
        } catch (SQLException e) {
            logg.info("Something went wrong");
        }
        return null;
    }

    @Override
    public Collection<Concert> findAll() {
        Collection<Concert> concerts = new ArrayList<>();
        Connection c = db.getConnection();
        try(PreparedStatement ps = c.prepareStatement("SELECT * FROM CONCERT")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String data_aux = rs.getString("Data");
                LocalDateTime d = LocalDateTime.parse(data_aux, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                Concert con = new Concert(rs.getInt(1), rs.getInt(2),
                        rs.getInt(3), rs.getInt(4), d);
                concerts.add(con);
            }
            return concerts;
        } catch (Exception e) {
            logg.info("Something went wrong");
        }
        return null;
    }

    @Override
    public int size() {
        logg.info("SIZE OF ConcertRepo");
        int s = 0;
        Connection c = db.getConnection();
        try (PreparedStatement statement = c.prepareStatement("SELECT COUNT(*) AS size FROM Concert")){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                s = rs.getInt("size");
                logg.info("SIZE = " + s + ", EXITING FUNCTION");
                return s;
            }
        } catch (SQLException e) {
            logg.info("Something went wrong");
        }
        return s;
    }

    @Override
    public Concert update(Concert nou) {
        return null;
    }

    @Override
    public void save(Concert concert) {

    }

    @Override
    public Concert delete(Integer integer) {
        return null;
    }

    @Override
    public List<ConcertDTO> findConcertsByDate(LocalDate d) {
        Connection connection = db.getConnection();
        List<ConcertDTO> lista = new ArrayList<>();
        String param = d.toString().concat("%");
        String comanda = "select * from (select c.ID, a.Nume as ARTIST , L.Nume as LOCATIE, c.Data, c.NrLocuri as LOCURI_OCUP, L.NrLocuri as NrLocuriTot from Concert c inner join Locatie L on L.LocID = c.Locatie inner join Artist a on a.ID = c.Artist) where Data like '" + param + "' ";
        try(PreparedStatement ps = connection.prepareStatement(comanda)){
            ResultSet r = ps.executeQuery();
            while(r.next()){
                int idc = r.getInt(1);
                String numea = r.getString(2);
                String numel = r.getString(3);
                String auxData = r.getString(4);
                LocalDateTime data = LocalDateTime.parse(auxData, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                int nrLocuriO = r.getInt(5);
                int nrLocuriTot = r.getInt(6);

                ConcertDTO rez = new ConcertDTO(idc, numea, numel, data, nrLocuriO, nrLocuriTot);
                lista.add(rez);
            }
        } catch (SQLException e) {
            logg.info(e.getMessage());
        }
        return lista;
    }

    @Override
    public void updateLocuriVandute(int idConcert, int nrLocuriNou) throws NuMaiSuntLocuriException {
        Connection conn = db.getConnection();
        logg.info("Entered update Locuri Vandute with concert id : " + idConcert + ", nrNou : " + nrLocuriNou);
        try(PreparedStatement ps = conn.prepareStatement("UPDATE CONCERT SET NrLocuri = ? WHERE ID = ?" )){
                ps.setInt(2,idConcert);
                ps.setInt(1,nrLocuriNou);
                ps.execute();
                logg.info("S-a executat update-ul");
        } catch (SQLException e) {
                logg.info(e.getMessage());
        }

        Connection con = db.getConnection();
        try(PreparedStatement ps = con.prepareStatement("SELECT NrLocuri from Concert where id = ?")){
            ps.setInt(1,idConcert);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                logg.info(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ConcertDTO> findConcertsDetails() {
        List<ConcertDTO> rezFin = new ArrayList<>();
        Connection con = db.getConnection();
        String comanda = "select c.ID, a.Nume as ARTIST , L.Nume as LOCATIE, c.Data, c.NrLocuri as LOCURI_OCUP, L.NrLocuri as NrLocuriTot from Concert c inner join Locatie L on L.LocID = c.Locatie inner join Artist a on a.ID = c.Artist ";

        try(PreparedStatement ps = con.prepareStatement(comanda)){
            ResultSet r = ps.executeQuery();
            while(r.next()){
                int idc = r.getInt(1);
                String numea = r.getString(2);
                String numel = r.getString(3);
                String auxData = r.getString(4);
                LocalDateTime data = LocalDateTime.parse(auxData, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                int nrLocuriO = r.getInt(5);
                int nrLocuriTot = r.getInt(6);

                ConcertDTO rez = new ConcertDTO(idc, numea, numel, data, nrLocuriO, nrLocuriTot);
                rezFin.add(rez);
            } }catch (SQLException e) {
            logg.info(e.getMessage());
        }
        return rezFin ;
    }
}
