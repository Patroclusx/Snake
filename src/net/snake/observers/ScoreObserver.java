package net.snake.observers;

import net.snake.game.StatusBar;

public class ScoreObserver implements  Observer{

    @Override
    public void update(Subject subject) {
        updateScore(subject);
    }

    /**
     * Updates the status bar
     * @param subject Subject of the observer
     */
    private void updateScore(Subject subject){
        StatusBar statusBar = (StatusBar)subject;

        String formatted = String.format("%07d", statusBar.getScore());
        statusBar.getScoreLabel().setText("Score " + formatted);
    }
}
