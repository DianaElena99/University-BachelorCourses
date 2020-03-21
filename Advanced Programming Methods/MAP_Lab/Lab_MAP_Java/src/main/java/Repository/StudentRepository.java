package Repository;

import Domain.Student;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;

import java.util.Collection;

public class StudentRepository extends AbstractRepository<Student, Integer> {

    public StudentRepository(Validator<Student> v) {
        super(v);
    }

    @Override
    public Collection<Student> findAll() {
        return super.findAll();
    }

    @Override
    public Student find(Integer integer) throws IllegalArgumentException {
        return super.find(integer);
    }

    @Override
    public Student save(Student obj) throws IllegalArgumentException, ValidatorException {
        return super.save(obj);
    }

    @Override
    public Student delete(Integer integer) throws IllegalArgumentException {
        return super.delete(integer);
    }

    @Override
    public Student update(Student obj) throws ValidatorException, IllegalArgumentException {
        return super.update(obj);
    }
    @Override
    public int size(){
        return super.size();
    }
}