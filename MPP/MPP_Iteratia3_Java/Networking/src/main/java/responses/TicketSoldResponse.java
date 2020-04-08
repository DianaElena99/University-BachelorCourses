package responses;

import Model.ConcertDTO;

import java.util.List;

public class TicketSoldResponse implements UpdateResponse {
    public List<ConcertDTO> actualizat;
    public TicketSoldResponse(List<ConcertDTO> c){
        actualizat=c;
    }
}
