package responses;

public class ErrorResponse implements Response{
    private String str;

    public ErrorResponse(String str) {
        this.str = str;
    }
}
