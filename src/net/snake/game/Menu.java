package net.snake.game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import net.snake.Main;

public class Menu extends Application {

    /**
     * Initialises and displays the menu window
     * @param primaryStage PrimaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 600, 400);

        //Set background image
        BackgroundImage myBI = new BackgroundImage(new Image(Main.class.getResourceAsStream("resources/bg.jpg"),600,400,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));

        //Add difficulty selector
        VBox vb = new VBox();
        Label label = new Label();
        label.setText("Select difficulty:");
        ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Easy");
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton("Medium");
        rb2.setToggleGroup(group);
        rb2.setSelected(true);
        RadioButton rb3 = new RadioButton("Unfair");
        rb3.setToggleGroup(group);

        //Add start button
        Button button = new Button("Start");
        button.setOnMouseClicked((MouseEvent event) ->{
            int difficulty = 0;

            if(rb1.isSelected()){
                difficulty = 0;
            }
            else if(rb2.isSelected()){
                difficulty = 1;
            }
            else if(rb3.isSelected()){
                difficulty = 2;
            }

            //Start the game
            try {
                new Snake(primaryStage, difficulty);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Add help button
        Button button2 = new Button("Help");
        button2.setOnMouseClicked((MouseEvent event) ->{
            String help = "SPACE to start snake, ARROWS to navigate  \n"+
                    "Red = Food \n" +
                    "Blue = Gate to next level \n\n" +
                    "Collect 15 food to unlock next level!";
            Main.infoBox(help, "Help");
        });

        //Add the elements to a vertical box
        vb.getChildren().addAll(label, rb1, rb2, rb3, button, button2);
        vb.setSpacing(10);
        vb.setAlignment(Pos.BOTTOM_CENTER);
        vb.setTranslateY(-20);

        //Add the elements to the root, set details of window and display it
        root.getChildren().add(vb);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Snake 2");
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * Lunches the menu window
     * @param args Launch arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
