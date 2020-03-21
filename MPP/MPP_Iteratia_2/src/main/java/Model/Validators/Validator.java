package Model.Validators;

public interface Validator<E> {
    void validate(E e) throws Exception;
}
