package net.snake.observers;

public interface Subject {

    /**
     * Register the obserevr to the subject
     * @param obj Observer to register
     */
    public void register(Observer obj);

    /**
     * Unregister the obserevr to the subject
     * @param obj Observer to unregister
     */
    public void unregister(Observer obj);

    /**
     * Notify all the observers of a subject of changes
     */
    public void notifyObservers();
}
