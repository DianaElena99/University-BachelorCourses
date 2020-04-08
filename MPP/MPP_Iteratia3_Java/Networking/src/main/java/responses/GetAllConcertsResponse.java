package responses;

import Model.ConcertDTO;

import java.util.List;

public class GetAllConcertsResponse implements Response{
    private List<ConcertDTO> concerts;

    public GetAllConcertsResponse(List<ConcertDTO> con){
        concerts = con;
    }

    public List<ConcertDTO> getConcerts() {
        return concerts;
    }
}
