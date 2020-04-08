package requests;

import Model.Bilet;

public class SellTicketRequest implements Request {
    private Bilet b;

    public SellTicketRequest(Bilet b) {
        this.b = b;
    }

    public Bilet getBilet() {
        return b;
    }
}
