package net.snake.game;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import net.snake.Main;

import java.net.URISyntaxException;

public class SoundHandler {

    private AudioClip foodSoundPlayer;
    private AudioClip hitSoundPlayer;
    private AudioClip completeSoundPlayer;

    public SoundHandler() throws URISyntaxException {
        Media foodSound = new Media(Main.class.getResource("resources/food.mp3").toURI().toString());
        Media hitSound = new Media(Main.class.getResource("resources/hit.mp3").toURI().toString());
        Media completeSound = new Media(Main.class.getResource("resources/complete.mp3").toURI().toString());

        foodSoundPlayer = new AudioClip(foodSound.getSource());
        hitSoundPlayer = new AudioClip(hitSound.getSource());
        completeSoundPlayer = new AudioClip(completeSound.getSource());
    }

    /**
     * Play the snake eating sound effect
     */
    public void playFoodSound(){
        foodSoundPlayer.play();
    }

    /**
     * Play the snake hit sound effect
     */
    public void playHitSound(){
        hitSoundPlayer.play();
    }

    /**
     * Play the level completted sound effect
     */
    public void playCompleteSound(){
        completeSoundPlayer.play();
    }
}
