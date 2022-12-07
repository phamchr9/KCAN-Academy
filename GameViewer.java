package boggle;
//import boggle.BoggleGame;
//import boggle.GameViewer;

/**
 * The Main class
 */

//public class Main {
//    /**
//    * Main method.
//    * @param args command line arguments.
//    **/
//    public static void main(String[] args) {
//        BoggleGame b = new BoggleGame();
//        GameViewer game = new GameViewer();
//
//        b.giveInstructions();
//        game.start(x, b);
//    }
//
//}
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.Node;

import java.awt.desktop.AppHiddenListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.awt.Rectangle;

/**
 * This is the main program.
 */
public class GameViewer extends Application {

    public static BoggleGame b;

    private AnchorPane anchorRoot; //root of the scene graph
    private final int h = 600; // dimensions for display
    private final int w = 600;

//    private BoggleGame bGame;

//    public GameViewer(boggle.BoggleGame b) {
//        bGame = b;
//    }


    /**
     * Main method
     * Needs to be static to run, but cannot access this.b this way
     * How would I pass in the string of letters?
     */
    public static void main(String[] args) {
        b = new BoggleGame();
//        GameViewer game = new GameViewer();

        b.giveInstructions();
        b.playGame();

        String l = b.boggleGrid.boardLetters;
        int a = b.allWords.size();
        String letters = "--letters=" + l;
        String totalWords = "--totalWords=" + Integer.toString(a);

        Application.launch(GameViewer.class, letters, totalWords);

//        launch(args);
    }

    /**
     * Start the visualization
     */
    public void start(Stage primaryStage, BoggleGame b) throws Exception {
        primaryStage.setTitle("Boggle");

        GridPane gridPane = new GridPane();


        // creating labels with letter input
        Label l1 = new Label("A");
        Label l2 = new Label("B");
        Label l3 = new Label("C");

        // styling labels
        l1.setStyle("-fx-border-color: blue; -fx-font-size: 60;");
        l2.setStyle("-fx-border-color: black; -fx-font-size: 60;");
        l3.setStyle("-fx-border-color: red; -fx-font-size: 60;");

        // Add items vertically using:
//        gridPane.addColumn(columnIndex, children);

        // Add items horizontally using:
        gridPane.add(l1, 0, 0, 1, 1);
        gridPane.add(l2, 0, 1, 1, 1);
        gridPane.add(l3, 1, 1, 1, 1);
        gridPane.setAlignment(Pos.CENTER);

        //attach all scene graph elements to the scene

        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setScene(scene); //set the scene ...
        primaryStage.show(); //... and ... go.
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parameters param = getParameters();
        String letters = param.getNamed().get("letters");
        String totalWords = param.getNamed().get("totalWords");

        stage.setTitle("Boggle");

        GridPane gridPane = new GridPane();

        // creating labels with letter input
        // loop through letters and create labels, style, add to board
        int index = 0;
        for (int i = 0; i < Math.sqrt(letters.length()); i++) {
            for (int j = 0; j < Math.sqrt(letters.length()); j++) {
                char c = letters.charAt(index);
                Label l = new Label(Character.toString(c));
                l.setStyle("-fx-border-color: blue; -fx-font-size: 60;");
                gridPane.add(l, j, i);
                index++;
            }
        }
            gridPane.setAlignment(Pos.CENTER);

            // Total word box:
            Label wordsLabel = new Label("Number of possible words in this grid: " + totalWords);
            HBox hbox = new HBox(wordsLabel);
            hbox.setAlignment(Pos.TOP_CENTER);


            //attach all scene graph elements to the scene
            BorderPane root = new BorderPane();
            root.setTop(gridPane);
            root.setBottom(hbox);

            Scene scene = new Scene(root, 500, 500);
            stage.setScene(scene); //set the scene ...
            stage.show(); //... and ... go.
        }
    }
