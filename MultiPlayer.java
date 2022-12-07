package boggle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.*;
import javax.swing.Timer;

public class MultiPlayer extends BoggleGame{

    /**
     * stores the initial amount of time for each players' turns.
     */
    final private int ROUND_TIME_LIMIT = 60;

    /**
     * stores whether the game is paused.
     */
    private boolean gameIsPaused;

    /**
     * stores the task that will execute after the timer delay.
     */
    private ActionListener task;

    /**
     * stores the time remaining (in seconds).
     */
    private int timeRemaining;

    /**
     * stores the timer for players turns.
     */
    private Timer timer;

    /**
     * stores whose turn it is currently (1 for Player 1; 2 for Player 2).
     */
    public String currentTurn;

    /**
     * scanner used to interact with the user via console.
     */
    public Scanner scanner;

    /**
     * stores game statistics.
     */
    private BoggleStats gameStats;

    public MultiPlayer(BoggleStats gameStats) {
        this.scanner = new Scanner(System.in);
        this.gameStats = gameStats;
        this.currentTurn = "1";  //Player 1 always goes first
        this.timeRemaining = ROUND_TIME_LIMIT;  //each player's turn is 60 seconds

        //sets the actions that will be performed after the timer delay
        this.task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining -= 1;
                if(timeRemaining%10 == 0){
                    System.out.println("");
                    System.out.println("you have " + timeRemaining + " seconds left");
                }

                if (timeRemaining == 0) {
                    timer.stop();
                    timeRemaining = ROUND_TIME_LIMIT;  //resets the timer
                    if (currentTurn.equals("1")) {
                        currentTurn = "2";
                        System.out.println("\nPlayer 1's turn has ended");
                        System.out.println("Press ENTER to start Player's 2 turn.");
                    } else if (currentTurn.equals("2")) {
                        currentTurn = "";
                        System.out.println("\nPlayer 2's turn has ended");
                        System.out.println("Press ENTER to end the round.");
                    }
                }
            }
        };

    }

    /*
     * Play a round of Boggle.
     * This initializes the main objects: the board, the dictionary, the map of all
     * words on the board, and the set of words found by the user. These objects are
     * passed by reference from here to many other functions.
     *
     * @param size An integer representation of the board dimensions (4 for 4-by-4 grid or 5 for 5-by-5 grid)
     * @param letters A String representation of the board letters (length 16 or 25 depending on the size of the grid)
     */

    public void playRound(int size, String letters){
        this.timer = new Timer(1000, task);  //initializes the timer delay
        this.gameIsPaused = false;

        //step 1. initialize the grid
        this.grid = new BoggleGrid(size);
        this.grid.initalizeBoard(letters);

        this.editor = new Editor(this.grid);
        this.allWords = new HashMap<String, ArrayList<Position>>();
        this.grid.takeSnapshot();
        System.out.println(this.grid.toString());

        boolean satisfied = false;

        //checks if the input is valid
        while(!satisfied){
            System.out.println("Press[S] to shuffle or Press[U] to undo shuffle or Press[P] to start playing [S/U/P]");
            String shuffle = scanner.nextLine().toUpperCase();
            if(shuffle.equals("S")){
                shuffle();
                System.out.println(this.grid.toString());
            } else if(shuffle.equals("P")) {
                satisfied = true;
            } else if(shuffle.equals("U")) {
                undo();
                System.out.println(this.grid.toString());
            } else {
                System.out.println("Invalid input, try again.");
            }
        }

        //step 2. initialize the dictionary of legal words
        Dictionary boggleDict = new Dictionary("wordlist.txt");

        //step 3. find all legal words on the board, given the dictionary and grid arrangement.
        findAllWords(boggleDict, this.grid);

        //step 4. allow Player 1 to try to find some words on the grid
        player1Move();

        //step 5. allow Player 2 to try to find some words on the grid
        player2Move();

        currentTurn = "1";  //reset current turn to Player 1

    }

    /*
     * Gets words from the user.  As words are input, check to see that they are valid.
     * If yes, add the word to Player 1's word list (in boggleStats) and increment
     * Player 1's score (in boggleStats).
     * End the turn once the user hits return (with no word).
     */

    private void player1Move(){

        timer.start();  //start the timer for Player 1's turn

        System.out.println("\nIt's Player 1's turn to find some words!\n");
        String inPut;

        while(true) {
            //step 1. Print the board for the user, so they can scan it for words
            if (!gameIsPaused) {
                System.out.println(this.grid.toString());
                System.out.print("Enter a word, press [P] to pause the game: ");
            }

            //step 2. Get a input (a word) from the user via the console
            inPut = scanner.nextLine().toUpperCase();

            if(currentTurn.equals("2")) {
                break; //end round if user inputs nothing
            } else if (inPut.equals("P")) {  //pauses the game
                timer.stop();
                gameIsPaused = true;
                System.out.println("Game paused. Press [S] to continue game.");
            } else if (inPut.equals("S") && gameIsPaused) {  //continues the game
                timer.start();
                gameIsPaused = false;
                System.out.println("Game is resumed.");
            }

            //step 3. Check to see if it is valid (note validity checks should be case-insensitive)
            if (this.allWords.containsKey((inPut.toUpperCase())) && !gameIsPaused) {
                //step 4. If it's valid, update the player's word list and score (stored in boggleStats)
                gameStats.addWord(inPut.toUpperCase(), BoggleStats.Player.Player1, "m");
            } else {
                playInvalid();
            }
        }

    }

    /*
     * Gets words from the user.  As words are input, check to see that they are valid.
     * If yes, add the word to Player 2's word list (in boggleStats) and increment
     * Player 2's score (in boggleStats).
     * End the turn once the user hits return (with no word).
     */

    private void player2Move(){

        timer.start();  //start the timer for Player 2's turn

        System.out.println("\nIt's Player 2's turn to find some words!\n");
        String inPut;

        while(true) {
            //step 1. Print the board for the user, so they can scan it for words
            if (!gameIsPaused) {
                System.out.println(this.grid.toString());
                System.out.print("Enter a word, press [P] to pause the game: ");
            }

            //step 2. Get a input (a word) from the user via the console
            inPut = scanner.nextLine().toUpperCase();

            if(currentTurn.equals("")) {
                break; //end round if user inputs nothing
            } else if (inPut.equals("P")) {  //pauses the game
                timer.stop();
                gameIsPaused = true;
                System.out.println("Game paused. Press [S] to continue game.");
            } else if (inPut.equals("S") && gameIsPaused) {  //continues the game
                timer.start();
                gameIsPaused = false;
                System.out.println("Game is resumed.");
            }

            //step 3. Check to see if it is valid (note validity checks should be case-insensitive)
            if (this.allWords.containsKey((inPut.toUpperCase())) && !gameIsPaused) {
                //step 4. If it's valid, update the player's word list and score (stored in boggleStats)
                gameStats.addWord(inPut.toUpperCase(), BoggleStats.Player.Player2, "m");
            } else {
                playInvalid();
            }
        }

    }
}
