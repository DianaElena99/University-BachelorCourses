package responses;

public class OkResponse implements Response{
    String mes;
    public OkResponse(String mesg){
        mes = mesg;
    }
}
