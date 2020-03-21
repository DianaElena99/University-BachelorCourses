package Repository;

import Model.Bilet;

public interface IRepoBiletUtils<ID,E> extends IRepo<Integer, Bilet>{
    void saveBilet(Bilet b);
}
