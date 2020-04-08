package responses;

import Model.ConcertDTO;

import java.util.List;

public class SearchConcertsResponse implements Response {
    public List<ConcertDTO> concerts;

    public SearchConcertsResponse(List<ConcertDTO> concerts) {
        this.concerts = concerts;
    }

    public List<ConcertDTO> getConcerts() {
        return concerts;
    }
}
