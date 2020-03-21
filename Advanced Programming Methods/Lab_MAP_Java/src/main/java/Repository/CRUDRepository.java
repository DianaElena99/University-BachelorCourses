package Repository;

import Domain.Validator.ValidatorException;
import Domain.Validator.IllegalArgumentException;

public interface CRUDRepository<ID, E>{


    E find(ID id) throws IllegalArgumentException;


    Iterable<E> findAll();

    E save(E entitiy) throws ValidatorException, IllegalArgumentException;

    E delete(ID id) throws IllegalArgumentException;

    E update(E entity) throws ValidatorException, IllegalArgumentException;

    int size();
}
