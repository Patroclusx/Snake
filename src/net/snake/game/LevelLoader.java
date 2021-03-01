package net.snake.game;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import net.snake.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URISyntaxException;

public class LevelLoader {

    private MusicHandler musicHandler;

    private BackgroundImage ground1BG;
    private BackgroundImage ground2BG;
    private BackgroundImage ground3BG;

    public LevelLoader() throws URISyntaxException {
        musicHandler = new MusicHandler();

        ground1BG = new BackgroundImage(new Image(Main.class.getResourceAsStream("resources/ground.jpg"),800,600,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        ground2BG = new BackgroundImage(new Image(Main.class.getResourceAsStream("resources/ground2.jpg"),800,600,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        ground3BG = new BackgroundImage(new Image(Main.class.getResourceAsStream("resources/ground3.jpg"),800,600,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
    }

    /**
     * Responsible to load the selected level and set the level's attributes
     * @param root The game's grid field
     * @param level Level to load MIN: 1 MAX: 6
     * @return
     */
    public boolean loadLevel(GridPane root, int level) {
        //Get the level image
        BufferedImage image = null;
        try {
            image = ImageIO.read(Main.class.getResourceAsStream("resources/maps/level"+level+".jpg"));
        } catch (Exception e) {
            System.err.println("ERROR: Could not load map!");
            e.printStackTrace();
            System.exit(0);
        }

        // Getting pixel color by position x and y
        if(image != null) {
            for (int y = 0; y < 50; y++) {
                for (int x = 0; x < 50; x++) {
                    int clr = image.getRGB(y, x);

                    int red = (clr & 0x00ff0000) >> 16;
                    int green = (clr & 0x0000ff00) >> 8;
                    int blue = clr & 0x000000ff;

                    Region regW = Main.getGrid(root, y, x);
                    regW.setId("empty");
                    regW.setStyle("-fx-background-color: transparent;");

                    //Look for black pixels and set them as walls
                    if ((red == 0 && green == 0 && blue == 0) || (red == 1 && green == 1 && blue == 1)) {
                        regW.setId("wall");
                        regW.setStyle("-fx-background-color: black;");
                    }
                }
            }

            //By every two level, change the background and music
            if(level <= 2) {
                setBackGround(root, 1);
                musicHandler.playTrack(1);
            }else if(level > 2 && level <= 4){
                setBackGround(root, 2);
                musicHandler.playTrack(2);
            }else{
                setBackGround(root, 3);
                musicHandler.playTrack(3);
            }

            return true;
        }
        return false;
    }

    /**
     * Sets the selected background of the level
     * @param root Root to set the background to
     * @param background Background index MIN: 1 MAX: 2
     */
    public void setBackGround(GridPane root, int background){
        switch(background) {
            case 1:
                root.setBackground(new Background(ground1BG));
                break;
            case 2:
                root.setBackground(new Background(ground2BG));
                break;
            case 3:
                root.setBackground(new Background(ground3BG));
                break;
            default:
                System.err.println("ERROR: Invalid level selected!");
                break;
        }
    }
}
