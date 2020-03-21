package Repository;
import Domain.Validator.IllegalArgumentException;
import Domain.Validator.Validator;
import Domain.Validator.ValidatorException;
import Domain.Entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Predicate;

public class AbstractRepository<E extends Entity<ID>, ID> implements CRUDRepository<ID, E> {

    private Validator<E> valid;
    private HashMap<ID, E> mapp;
    private Predicate<E> nullElement = Objects::isNull;
    private Predicate<ID> nullID = Objects::isNull;

    AbstractRepository(Validator<E> v){
        this.valid = v;
        mapp = new HashMap<>();
    }

    public Collection<E> findAll(){
        return mapp.values();
    }

    public E find(ID id) throws IllegalArgumentException {
        if (nullID.test(id))
           throw new IllegalArgumentException("ID CANNOT BE NULL");
        return mapp.get(id);
    }

    public E save(E obj) throws IllegalArgumentException, ValidatorException{
        if (nullElement.test(obj))
            throw new IllegalArgumentException("Obj can't be null");
        valid.validate(obj);
        E elem = find(obj.getID());
        if (nullElement.test(elem))
            mapp.put(obj.getID(), obj);
        return elem;
    }

    public E delete(ID id) throws IllegalArgumentException{
        if (nullID.test(id))
            throw new IllegalArgumentException("ID can't be null");
        E elem = mapp.get(id);
        if (nullElement.test(elem))
            return null;
        mapp.remove(id);
        return elem;
    }

    public E update(E obj) throws ValidatorException, IllegalArgumentException{
        if (nullElement.test(obj))
            throw new IllegalArgumentException("Object cannot be null");
        E elem = mapp.get(obj.getID());
        if (!nullElement.test(elem)){
            mapp.remove(elem.getID(),elem);
            mapp.put(obj.getID(),obj);
            return elem;
        }
        this.save(obj);
        return null;
    }

    public int size(){
        return mapp.size();
    }
}
