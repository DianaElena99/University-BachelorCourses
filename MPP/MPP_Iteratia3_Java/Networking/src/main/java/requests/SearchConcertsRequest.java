package requests;

import java.time.LocalDate;

public class SearchConcertsRequest implements Request {
    private LocalDate data;

    public SearchConcertsRequest(LocalDate data) {
        this.data = data;
    }

    public LocalDate getData() {
        return data;
    }
}
