package Domain.Validator;

public interface Validator<E> {
    void validate(E entity) throws
            ValidatorException, IllegalArgumentException;

}
