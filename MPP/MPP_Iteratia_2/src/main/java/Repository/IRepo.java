package Repository;

import java.util.Collection;

public interface IRepo <ID, E> {
    void save(E e);
    E delete(ID id);
    E update(E nou);
    E findOne(ID id);
    Collection<E> findAll();
    int size();
}
