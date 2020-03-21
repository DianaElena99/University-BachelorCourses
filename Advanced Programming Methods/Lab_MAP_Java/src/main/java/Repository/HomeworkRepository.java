package Repository;

import Domain.Homework;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;

import java.util.Collection;

public class HomeworkRepository extends AbstractRepository<Homework, Integer>{

    public HomeworkRepository(Validator<Homework> v) {
        super(v);
    }

    @Override
    public Collection<Homework> findAll() {
        return super.findAll();
    }

    @Override
    public Homework find(Integer integer) throws  IllegalArgumentException {
        return super.find(integer);
    }

    @Override
    public Homework save(Homework obj) throws IllegalArgumentException, ValidatorException {
        return super.save(obj);
    }

    @Override
    public Homework delete(Integer integer) throws IllegalArgumentException {
        return super.delete(integer);
    }

    @Override
    public int size(){
        return super.size();
    }
}
