package net.snake.game;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import net.snake.observers.*;

import java.util.ArrayList;
import java.util.List;

public class StatusBar implements Subject {

    private List<Observer> observers;

    //Stats
    private int lives = 3;
    private int score = 0;
    private int foodEaten = 0;

    //Status Bar
    private HBox statusBar = new HBox();
    private Label livesLabel = new Label();
    private Label scoreLabel = new Label();
    private Label foodLabel = new Label();

    public StatusBar(){
        this.observers = new ArrayList<>();

        registerObservers();
        createStatusBar();
    }

    /**
     * Initialises the status bar
     */
    private void createStatusBar(){
        String style = "-fx-font-size: 20px; -fx-font-family: \"Arial Black\"; -fx-text-fill: red;";
        livesLabel.setStyle(style);
        scoreLabel.setStyle(style);
        foodLabel.setStyle(style);
        statusBar.setSpacing(230);
        notifyObservers();
        statusBar.getChildren().addAll(livesLabel, scoreLabel, foodLabel);
        statusBar.setMaxHeight(42);
    }

    /**
     * Registers all the corresponding observers
     */
    private void registerObservers(){
        register(new LivesObserver());
        register(new ScoreObserver());
        register(new FoodObserver());
    }

    @Override
    public void register(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        List<Observer> observersLocal = new ArrayList<>(this.observers);

        for (Observer obj : observersLocal) {
            obj.update(this);
        }
    }


    /**
     * @return Return the whole status bar as a node
     */
    public Node getStatusBar(){
        return statusBar;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
        notifyObservers();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        notifyObservers();
    }

    public int getFoodEaten() {
        return foodEaten;
    }

    public void setFoodEaten(int foodEaten) {
        this.foodEaten = foodEaten;
        notifyObservers();
    }

    public Label getLivesLabel() {
        return livesLabel;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public Label getFoodLabel() {
        return foodLabel;
    }
}
