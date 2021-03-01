package net.snake.states;

import net.snake.game.Snake;

public class SnakeAliveState extends SnakeState{

    //Singleton
    private static SnakeAliveState instance = new SnakeAliveState();

    private SnakeAliveState() {}

    public static SnakeAliveState instance() {
        return instance;
    }

    /**
     * If snake is alive, initiate the main game loop
     * DEFAULT when game started or level loaded
     * @param snake The snake game object
     */
    @Override
    public void updateSnakeState(Snake snake) {
        snake.gameTick();
    }
}
