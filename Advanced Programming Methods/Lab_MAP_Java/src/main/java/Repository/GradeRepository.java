package Repository;
import Domain.Grade;
import Domain.Validator.GradeValidator;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;

import java.util.Collection;

public class GradeRepository extends AbstractRepository<Grade, String>{
    public GradeRepository(Validator<Grade> v) {
        super(v);
    }

    @Override
    public Grade save(Grade obj) throws IllegalArgumentException, ValidatorException {
        return super.save(obj);
    }

    @Override
    public Grade delete(String s) throws IllegalArgumentException {
        return super.delete(s);
    }

    @Override
    public Grade update(Grade obj) throws ValidatorException, IllegalArgumentException {
        return super.update(obj);
    }

    @Override
    public Grade find(String s) throws IllegalArgumentException {
        return super.find(s);
    }

    @Override
    public Collection<Grade> findAll() {
        return super.findAll();
    }
}
