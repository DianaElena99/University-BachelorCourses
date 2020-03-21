package Domain.Validator;

import Domain.Grade;

import java.util.Objects;
import java.util.function.Predicate;

public class GradeValidator implements Validator<Grade> {
    private Predicate<Grade> nullGrade = Objects::isNull;
    private Predicate<Float> invalidValue = x -> x>11 || x < 0;
    @Override
    public void validate(Grade entity) throws ValidatorException, IllegalArgumentException {
        if (nullGrade.test(entity))
            throw new IllegalArgumentException("Grade cannot be null\n");
        String errs = "";
        if (invalidValue.test(entity.getNota()))
            errs += "Nota trebuie sa fie intre 1-10";
        if (entity.getID().equals("_"))
            errs += "ID invalid\n";
        if (entity.getProfesor().equals(""))
            errs += "Prof cannot be empty string\n";

        if (!errs.equals(""))
            throw new ValidatorException(errs);
    }
}
