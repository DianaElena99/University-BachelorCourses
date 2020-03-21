package Domain.Validator;

public class ValidatorException extends Exception {
    public ValidatorException(String err){
        super(err);
    }
    public ValidatorException(){}
}
