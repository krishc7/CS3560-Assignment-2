package app;

// Subject class for Observer design pattern

public interface Subject {

    public void attach(Observer observer);
    public void notifyObservers();

}
