package net.snake.observers;

import net.snake.game.StatusBar;

public class FoodObserver implements Observer{

    @Override
    public void update(Subject subject) {
        updateFoodEaten(subject);
    }

    /**
     * Updates the status bar
     * @param subject Subject of the observer
     */
    private void updateFoodEaten(Subject subject){
        StatusBar statusBar = (StatusBar)subject;

         if(statusBar.getFoodEaten() < 10) {
             statusBar.getFoodLabel().setText("Food " + (10 - statusBar.getFoodEaten()));
         }else{
             statusBar.getFoodLabel().setText("BONUS");
         }
    }
}
