package Domain.Validator;

public class IllegalArgumentException extends Exception {
    public IllegalArgumentException(String err){
        super(err);
    }
    public IllegalArgumentException(){}
}
