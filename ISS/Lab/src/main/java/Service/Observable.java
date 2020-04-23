package Service;

public interface Observable {
    void addObserver(Observer o);
    void notifyObservers(TipEvent te);
}
