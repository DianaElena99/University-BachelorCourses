package requests;

import Model.ConcertDTO;

import java.util.List;

public class UpdateRequest implements Request {
    public List<ConcertDTO> l;
    public UpdateRequest(List<ConcertDTO> l){
        this.l=l;
    }
}
