package repo;

import Model.Concert;
import Model.ConcertDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConcertRepo {
    public DBUtils dbstuff;

    public ConcertRepo(){
        dbstuff = new DBUtils();
    }

    public List<ConcertDTO> findConcertsDetails() {
        List<ConcertDTO> rezFin = new ArrayList<>();
        Connection con = dbstuff.getConnection();
        String comanda = "select c.ID, a.Nume as ARTIST , L.Nume as LOCATIE, c.Data, c.NrLocuri as LOCURI_OCUP, L.NrLocuri as NrLocuriTot from Concert c inner join Locatie L on L.id = c.Locatie inner join Artist a on a.ID = c.Artist ";

        try(PreparedStatement ps = con.prepareStatement(comanda)){
            ResultSet r = ps.executeQuery();
            while(r.next()){
                int idc = r.getInt(1);
                String numea = r.getString(2);
                String numel = r.getString(3);
                String auxData = r.getString(4);
                //LocalDateTime data = LocalDateTime.parse(auxData, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                //LocalDateTime data = LocalDateTime.now();
                int nrLocuriO = r.getInt(5);
                int nrLocuriTot = r.getInt(6);

                ConcertDTO rez = new ConcertDTO(idc, numea, numel, auxData, nrLocuriO, nrLocuriTot);
                rezFin.add(rez);
            } }catch (SQLException e) {
            e.printStackTrace();
        }
        return rezFin ;
    }

    public int size() {
        System.out.println("SIZE OF ConcertRepo");
        int s = 0;
        Connection c = dbstuff.getConnection();
        try (PreparedStatement statement = c.prepareStatement("SELECT COUNT(*) AS size FROM Concert")){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                s = rs.getInt("size");
                System.out.print("SIZE = " + s + ", EXITING FUNCTION");
                return s;
            }
        } catch (SQLException e) {
            System.out.print("Something went wrong");
        }
        return s;
    }


    public List<ConcertDTO> findConcertsByDate(LocalDate d) {
        Connection connection = dbstuff.getConnection();
        List<ConcertDTO> lista = new ArrayList<>();
        String param = d.toString().concat("%");
        String comanda = "select * from (select c.ID, a.Nume as ARTIST , L.Nume as LOCATIE, c.Data, c.NrLocuri as LOCURI_OCUP, L.NrLocuri as NrLocuriTot from Concert c inner join Locatie L on L.id = c.Locatie inner join Artist a on a.ID = c.Artist) where Data like '" + param + "' ";
        try(PreparedStatement ps = connection.prepareStatement(comanda)){
            ResultSet r = ps.executeQuery();
            while(r.next()){
                int idc = r.getInt(1);
                String numea = r.getString(2);
                String numel = r.getString(3);
                String auxData = r.getString(4);
                //LocalDateTime data = LocalDateTime.parse(auxData, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                int nrLocuriO = r.getInt(5);
                int nrLocuriTot = r.getInt(6);

                ConcertDTO rez = new ConcertDTO(idc, numea, numel, auxData, nrLocuriO, nrLocuriTot);
                lista.add(rez);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }
    public void updateLocuriVandute(int idConcert, int nrLocuriNou)  {
        Connection conn = dbstuff.getConnection();
        System.out.println("Entered update Locuri Vandute with concert id : " + idConcert + ", nrNou : " + nrLocuriNou);
        try(PreparedStatement ps = conn.prepareStatement("UPDATE CONCERT SET NrLocuri = ? WHERE ID = ?" )){
            ps.setInt(2,idConcert);
            ps.setInt(1,nrLocuriNou);
            ps.execute();
            System.out.println("S-a executat update-ul");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Connection con = dbstuff.getConnection();
        try(PreparedStatement ps = con.prepareStatement("SELECT NrLocuri from Concert where id = ?")){
            ps.setInt(1,idConcert);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Concert findOne(Integer integer) {
        if (integer < 0){
            return null;
        }
        Concert target;
        Connection c = dbstuff.getConnection();
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
                return con;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}