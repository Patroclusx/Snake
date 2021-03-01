package net.snake.observers;

public interface Observer {

    /**
     * Called when the observer receives an update request
     * @param subject Subject of the observer
     */
    public void update(Subject subject);
}
