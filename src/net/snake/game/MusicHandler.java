package net.snake.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import net.snake.Main;

import java.net.URISyntaxException;

public class MusicHandler {

    private MediaPlayer music1PLayer;
    private MediaPlayer music2PLayer;
    private MediaPlayer music3PLayer;

    public MusicHandler() throws URISyntaxException {
        Media music1 = new Media(Main.class.getResource("resources/music1.mp3").toURI().toString());
        Media music2 = new Media(Main.class.getResource("resources/music2.mp3").toURI().toString());
        Media music3 = new Media(Main.class.getResource("resources/music3.mp3").toURI().toString());

        music1PLayer = new MediaPlayer(music1);
        music1PLayer.setOnEndOfMedia(() -> music1PLayer.seek(Duration.ZERO));
        music2PLayer = new MediaPlayer(music2);
        music2PLayer.setOnEndOfMedia(() -> music2PLayer.seek(Duration.ZERO));
        music3PLayer = new MediaPlayer(music3);
        music3PLayer.setOnEndOfMedia(() -> music3PLayer.seek(Duration.ZERO));
    }

    /**
     * Plays the specified background music
     * @param track Track to play MIN: 1 MAX: 3
     */
    public void playTrack(int track){
        stopAllTracks();

        switch(track) {
            case 1:
                music1PLayer.play();
                break;
            case 2:
                music2PLayer.play();
                break;
            case 3:
                music3PLayer.play();
                break;
            default:
                System.err.println("ERROR: Invalid track selected!");
                break;
        }
    }

    /**
     * Stops all playing background music
     */
    public void stopAllTracks(){
        if(music1PLayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
            music1PLayer.stop();
        }
        if(music2PLayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
            music2PLayer.stop();
        }
        if(music3PLayer.getStatus().equals(MediaPlayer.Status.PLAYING)){
            music3PLayer.stop();
        }
    }
}
