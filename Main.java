package boggle;

import boggle.BoggleGame;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.scene.media.*;
import java.io.File;
import java.util.*;

/**
 * The Main class for the first Assignment in CSC207, Fall 2022
 */
public class Main {
    /**
    * Main method. 
    * @param args command line arguments.
    **/
    public static void main(String[] args) {
//        String path;
//        Platform.startup({
//                path = "C:\\Users\\Kenaz Christian\\OneDrive\\Desktop\\University\\Courses\\CSC207\\KCAN-Academy\\CorrectSound.mp3";
//            File file = new File(path);
//            String uri = file.toURI().toString();
//            Media media = new Media(uri);
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.play();
//        });


        BoggleGame b = new BoggleGame();
        b.giveInstructions();
        b.playGame();
    }

}
