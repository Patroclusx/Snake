package net.snake.states;

import net.snake.game.Snake;

public abstract class SnakeState {

    /**
     * Updates the state of the snake
     * @param snake The snake game object
     */
    public abstract void updateSnakeState(Snake snake);

}
