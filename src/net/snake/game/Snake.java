package net.snake.game;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.snake.Main;
import net.snake.states.SnakeAliveState;
import net.snake.states.SnakeDeadState;
import net.snake.states.SnakeState;

import java.net.URISyntaxException;
import java.util.Random;

public class Snake {

    private GridPane gridField;
    private Timeline gameLoop;
    private int difficulty;

    private int level = 1;
    private boolean travelling = false;

    //Snake Model Singleton Instance
    private ModelSnake snakeModel = ModelSnake.getInstance();

    //Status bar
    private StatusBar statusBar = new StatusBar();

    //Snake state
    private SnakeState snakeState;

    private LevelLoader levelLoader;
    private SoundHandler soundHandler;

    public Snake(Stage primaryStage, int diff) throws URISyntaxException {
        this.difficulty = diff;

        //Setting default state
        if (snakeState == null) {
            snakeState = SnakeAliveState.instance();
        }

        levelLoader = new LevelLoader();
        soundHandler = new SoundHandler();

        createScene(primaryStage);
    }

    /**
     * Initialise the game window and its elements, display it and update the game state
     * @param primaryStage
     */
    private void createScene(Stage primaryStage) {
        SplitPane root = new SplitPane();
        gridField = new GridPane();

        gridField.setMaxSize(800, 550);

        //Create level grid (50x50)
        for (int y = 0; y < 50; y++) {
            for (int x = 0; x < 50; x++) {
                Region reg = new Region();
                reg.setMinSize(gridField.getMaxWidth()/50, gridField.getMaxHeight()/50);
                gridField.add(reg, x, y);
                GridPane.setRowIndex(reg, x);
                GridPane.setColumnIndex(reg, y);
                reg.setId("empty");
            }
        }

        //Position elements
        root.getItems().addAll(statusBar.getStatusBar(), gridField);
        root.setOrientation(Orientation.VERTICAL);
        root.setDividerPosition(0, 0);

        //Set default background colour
        root.setStyle("-fx-background-color: #96c503");

        //Display the scene
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.sizeToScene();
        gridField.requestFocus();

        initGame();
    }

    /**
     * Loads the level, adds the default snake to it and generates the first food item
     */
    public void initGame() {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        sleeper.setOnSucceeded(event1 -> {
            //Check if dead
            if (statusBar.getLives() <= 0) {
                Main.infoBox("YOU ARE DEAD! \n Final score: " + statusBar.getScore(), "Game Over");
                System.exit(0);
            }

            levelLoader.loadLevel(gridField, level);
            addSnake(25, 10);
            travelling = false;
            generateFood();
        });

        new Thread(sleeper).start();
    }


    /**
     * @return Returns the current state the snake is in
     */
    public SnakeState getCurrentState() {
        return snakeState;
    }

    /**
     * Sets a new state for the snake
     * @param currentState The new state
     */
    public void setCurrentState(SnakeState currentState) {
        this.snakeState = currentState;
        update();
    }

    /**
     * Update the state if it has been changed
     */
    public void update() {
        snakeState.updateSnakeState(this);
    }


    /**
     * Creates a delayed loop on a new thread
     * Limited to maximum 60 Frames per Second
     */
    public void gameTick() {
        //Delay is set by the difficulty
        int[] time = new int[]{140, 90, 60};

        gameLoop = new Timeline(new KeyFrame(Duration.millis(time[difficulty]), e -> {
            //Game tick event
            if (!snakeModel.getDirectionArray().contains("STILL")) {
                moveAndUpdateSnake(snakeModel.getModelArray().get(snakeModel.getModelArray().toArray().length - 1), snakeModel.getDirectionArray().get(0));
                if (snakeModel.getDirectionArray().toArray().length > 1 && !travelling) {
                    snakeModel.getDirectionArray().remove(0);
                }
            }
        }));

        //Start the loop
        gameLoop.setCycleCount(Animation.INDEFINITE);
        gameLoop.play();
    }


    /**
     * Generates a new food source on the level
     */
    private void generateFood() {
        ObservableList<Node> nodes = gridField.getChildren();
        while (true) {
            int rand = new Random().nextInt((nodes.toArray().length - 1));
            Region reg = (Region) nodes.get(rand);
            if (reg.getId().contains("empty")) {
                reg.setId("food");
                reg.setStyle("-fx-background-color: red; -fx-border-style: solid; -fx-border-width: 2px;");
                break;
            }
        }
    }


    /**
     * Generates the end gate to exit the level
     */
    private void generateEnd() {
        ObservableList<Node> nodes = gridField.getChildren();
        while (true) {
            int rand = new Random().nextInt((nodes.toArray().length - 1));
            Region reg = (Region) nodes.get(rand);
            if (reg.getId().contains("empty")) {
                reg.setId("end");
                reg.setStyle("-fx-background-color: aqua; -fx-border-style: solid; -fx-border-width: 2px;");
                break;
            }
        }
    }

    /**
     * Adds the snake to the current level on a specified location.
     * The location represents the head of the snake
     * @param x Column index
     * @param y Row index
     */
    private void addSnake(int x, int y) {
        //Sets snake to be still at start
        snakeModel.getDirectionArray().add("STILL");

        //Add snake model to GridPane
        gridField.getChildren().addAll(ModelSnake.getInstance().getModelArray());
        ModelSnake.getInstance().setModelAtPosition(x, y);
        createSnakeKeyHandler();
        setCurrentState(SnakeAliveState.instance());
    }

    /**
     * Moves the snake by 1 grid in respect to the direction
     * Checks for elements the head can collide with to determine actions
     * @param regH The current head position
     * @param direction The direction the snake is heading to
     */
    private void moveAndUpdateSnake(Region regH, String direction) {
        Region newRegH = null;

        //Remove the tail
        snakeModel.getModelArray().get(0).setId("empty");
        snakeModel.getModelArray().get(0).setStyle("-fx-background-color: transparent;");
        snakeModel.getModelArray().remove(0);

        //Check direction and set the new snake head position
        if (direction.contains("UP")) {
            newRegH = Main.getGrid(gridField, GridPane.getColumnIndex(regH), GridPane.getRowIndex(regH) - 1);
            if (newRegH == null) {
                newRegH = Main.getGrid(gridField, GridPane.getColumnIndex(regH), 49);
            }
        } else if (direction.contains("DOWN")) {
            newRegH = Main.getGrid(gridField, GridPane.getColumnIndex(regH), GridPane.getRowIndex(regH) + 1);
            if (newRegH == null) {
                newRegH = Main.getGrid(gridField, GridPane.getColumnIndex(regH), 0);
            }
        } else if (direction.contains("LEFT")) {
            newRegH = Main.getGrid(gridField, GridPane.getColumnIndex(regH) - 1, GridPane.getRowIndex(regH));
            if (newRegH == null) {
                newRegH = Main.getGrid(gridField, 49, GridPane.getRowIndex(regH));
            }
        } else if (direction.contains("RIGHT")) {
            newRegH = Main.getGrid(gridField, GridPane.getColumnIndex(regH) + 1, GridPane.getRowIndex(regH));
            if (newRegH == null) {
                newRegH = Main.getGrid(gridField, 0, GridPane.getRowIndex(regH));
            }
        }

        //Change head into a body element
        if (snakeModel.getModelArray().toArray().length > 1) {
            snakeModel.getModelArray().get(snakeModel.getModelArray().toArray().length - 1).setId("body");
            snakeModel.getModelArray().get(snakeModel.getModelArray().toArray().length - 1).setStyle("-fx-background-color: green; -fx-border-style: solid; -fx-border-width: 0.5px;");
        }

        //Check if body or wall is hit
        if (newRegH.getId().contains("body") || newRegH.getId().contains("wall")) {
            soundHandler.playHitSound();
            statusBar.setLives(statusBar.getLives() - 1);
            statusBar.setFoodEaten(0);

            setCurrentState(SnakeDeadState.instance());
            gameLoop.pause();
        }

        //Check if food was hit
        if (newRegH.getId().contains("food")) {
            int sizeByDifficulty = difficulty > 0 ? 2 : 1;

            snakeModel.growSnake(sizeByDifficulty);

            generateFood();
            statusBar.setFoodEaten(statusBar.getFoodEaten() + 1);

            soundHandler.playFoodSound();

            if (statusBar.getFoodEaten() == 10) {
                generateEnd();
            }

            int endBonus = statusBar.getFoodEaten() > 10 ? 10 : 0;
            statusBar.setScore(statusBar.getScore() + 10 * (difficulty + 1) + endBonus);
        }

        //Check if end gate is entered
        if (newRegH.getId().contains("end")) {
            if (snakeModel.getModelArray().toArray().length <= 0) {
                //When travelling is done
                level += 1;
                statusBar.setFoodEaten(0);
                if (level <= 6) {
                    setCurrentState(SnakeDeadState.instance());
                    gameLoop.pause();
                } else {
                    Main.infoBox("YOU HAVE FINISHED THE GAME! \n Final score: " + statusBar.getScore(), "Congratulations");
                    System.exit(0);
                }
            } else {
                //While travelling
                if (travelling == false) {
                    soundHandler.playCompleteSound();
                    travelling = true;
                    gameLoop.setRate(2);
                }
                statusBar.setScore(statusBar.getScore() + 10 * (difficulty + 1));
            }
        }

        //Add head to the new position
        if (newRegH.getId().contains("empty") || newRegH.getId().contains("food")) {
            snakeModel.getModelArray().add(newRegH);
            newRegH.setStyle("-fx-background-color: lime; -fx-border-style: solid; -fx-border-width: 1.5px;");
            newRegH.setId("head");
        }
    }

    /**
     * Assigns a key handler to the game window
     */
    private void createSnakeKeyHandler() {
        if (gridField.getOnKeyPressed() == null) {
            gridField.setOnKeyPressed(keyEvent -> {
                KeyCode keyCode = keyEvent.getCode();
                //If snake is still, only Space is accepted as an input
                if (!snakeModel.getDirectionArray().contains("STILL")) {
                    //Check if directional keys are pressed and if acceptable, add the new instruction into the direction array
                    if (keyCode.equals(KeyCode.W) || keyCode.equals(KeyCode.UP)) {
                        if (!snakeModel.getDirectionArray().get(snakeModel.getDirectionArray().toArray().length - 1).contains("DOWN")) {
                            if (snakeModel.getDirectionArray().toArray().length < 3 && !snakeModel.getDirectionArray().get(snakeModel.getDirectionArray().toArray().length - 1).contains("UP")) {
                                snakeModel.getDirectionArray().add("UP");
                            }
                        }
                    }
                    if (keyCode.equals(KeyCode.S) || keyCode.equals(KeyCode.DOWN)) {
                        if (!snakeModel.getDirectionArray().get(snakeModel.getDirectionArray().toArray().length - 1).contains("UP")) {
                            if (snakeModel.getDirectionArray().toArray().length < 3 && !snakeModel.getDirectionArray().get(snakeModel.getDirectionArray().toArray().length - 1).contains("DOWN")) {
                                snakeModel.getDirectionArray().add("DOWN");
                            }
                        }
                    }
                    if (keyCode.equals(KeyCode.A) || keyCode.equals(KeyCode.LEFT)) {
                        if (!snakeModel.getDirectionArray().get(snakeModel.getDirectionArray().toArray().length - 1).contains("RIGHT")) {
                            if (snakeModel.getDirectionArray().toArray().length < 3 && !snakeModel.getDirectionArray().get(snakeModel.getDirectionArray().toArray().length - 1).contains("LEFT")) {
                                snakeModel.getDirectionArray().add("LEFT");
                            }
                        }
                    }
                    if (keyCode.equals(KeyCode.D) || keyCode.equals(KeyCode.RIGHT)) {
                        if (!snakeModel.getDirectionArray().get(snakeModel.getDirectionArray().toArray().length - 1).contains("LEFT")) {
                            if (snakeModel.getDirectionArray().toArray().length < 3 && !snakeModel.getDirectionArray().get(snakeModel.getDirectionArray().toArray().length - 1).contains("RIGHT")) {
                                snakeModel.getDirectionArray().add("RIGHT");
                            }
                        }
                    }
                } else {
                    if (keyCode.equals(KeyCode.SPACE)) {
                        snakeModel.getDirectionArray().clear();
                        snakeModel.getDirectionArray().add("RIGHT");
                    }
                }
            });
        }
    }
}