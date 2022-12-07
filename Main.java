package boggle;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.scene.media.*;
import java.io.File;
import java.util.*;

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
        BoggleGame b = new BoggleGame();
        b.giveInstructions();
        b.setGamemode();
        b.playGame();
    }

}
