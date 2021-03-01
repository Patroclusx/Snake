package net.snake.states;

import net.snake.game.ModelSnake;
import net.snake.game.Snake;

public class SnakeDeadState extends SnakeState {

    //Singleton
    private static SnakeDeadState instance = new SnakeDeadState();

    private SnakeDeadState() {}

    public static SnakeDeadState instance() {
        return instance;
    }

    /**
     * If snake is dead or travelling to a new level, reset the snake and reload or load the level
     * @param snake The snake game object
     */
    @Override
    public void updateSnakeState(Snake snake) {
        ModelSnake.getInstance().resetSnake();
        snake.initGame();
    }
}
