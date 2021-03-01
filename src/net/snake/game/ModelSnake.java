package net.snake.game;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class ModelSnake {

    private static ModelSnake snakeModelInstance;
    private ArrayList<Region> snakeModel;

    private ArrayList<String> direction = new ArrayList<>();

    private ModelSnake(){
        snakeModel = new ArrayList<>();
        addDirection("STILL");
        bakeModel();
    }

    public static ModelSnake getInstance(){
        if(snakeModelInstance == null) {
            snakeModelInstance = new ModelSnake();
        }

        return snakeModelInstance;
    }

    /**
     * Creates the default snake model
     */
    public void bakeModel(){
        Region regHead = new Region();
        regHead.setId("head");
        regHead.setStyle("-fx-background-color: lime; -fx-border-style: solid; -fx-border-width: 1.5px;");
        for (int i = 5; i >= 1; i--) {
            Region regBody = new Region();
            regBody.setId("body");
            regBody.setStyle("-fx-background-color: green; -fx-border-style: solid; -fx-border-width: 0.5px;");
            snakeModel.add(regBody);
        }
        snakeModel.add(regHead);
    }

    /**
     * Sets the head of the snake model to the specified location
     * @param x Row index
     * @param y Column index
     */
    public void setModelAtPosition(int x, int y){
        int offsetY = 0;
        for (int i = snakeModel.toArray().length-1; i >= 0; i--) {
            GridPane.setRowIndex(getModelArray().get(i), x);
            GridPane.setColumnIndex(getModelArray().get(i), y - offsetY);
            offsetY++;
        }
    }

    public ArrayList<Region> getModelArray(){
        return snakeModel;
    }

    /**
     * Extends the snake size by the specified amount
     * @param ammount The amount to extend the snake's body by
     */
    public void growSnake(int ammount){
        for(int i = 0; i < ammount; i++) {
            Region regBody = new Region();
            regBody.setId("body");
            regBody.setStyle("-fx-background-color: green; -fx-border-style: solid; -fx-border-width: 0.5px;");
            snakeModel.add(regBody);
        }
    }

    /**
     * Resets the snake to its default state
     */
    public void resetSnake(){
        snakeModel.clear();
        direction.clear();
        bakeModel();
        addDirection("STILL");
    }

    public ArrayList<String> getDirectionArray() {
        return direction;
    }

    /**
     * Adds any new direction instructions to the direction array
     * @param direction STILL, UP, DOWN, LEFT, RIGHT
     */
    public void addDirection(String direction) {
        this.direction.add(direction);
    }
}
