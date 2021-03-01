package net.snake.observers;

import net.snake.game.StatusBar;

public class LivesObserver implements Observer {

    @Override
    public void update(Subject subject) {
        updateLives(subject);
    }

    /**
     * Updates the status bar
     * @param subject Subject of the observer
     */
    private void updateLives(Subject subject){
        StatusBar statusBar = (StatusBar)subject;

        statusBar.getLivesLabel().setText("Lives "+ statusBar.getLives());
    }
}
