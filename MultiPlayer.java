package boggle;

import java.util.*;

public class MultiPlayer extends BoggleGame{

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


    }

    /*
     * Play a round of Boggle.
     * This initializes the main objects: the board, the dictionary, the map of all
     * words on the board, and the set of words found by the user. These objects are
     * passed by reference from here to many other functions.
     *
     * @param size An integer representation of the board dimensions (4-by-4 or 5-by-5)
     * @param letters A String representation of the board letters
     */

    public void playRound(int size, String letters){

        //step 1. initialize the grid
        this.grid = new BoggleGrid(size);
        grid.initalizeBoard(letters);

        this.editor = new Editor(this.grid);
        this.allWords = new HashMap<String, ArrayList<Position>>();
        this.grid.takeSnapshot();
        System.out.println(this.grid.toString());

        boolean satisfied = false;

        while(!satisfied){
            System.out.println("Press[S] to shuffle or Press[U] to undo shuffle or Press[P] to start playing [S/U/P]");
            String shuffle = scanner.nextLine().toUpperCase();
            if(shuffle.equals("S")){
                shuffle();
                System.out.println(this.grid.toString());
            }
            else if(shuffle.equals("P"))
                satisfied = true;
            else if(shuffle.equals("U")) {
                undo();
                System.out.println(this.grid.toString());
            }
            else
                System.out.println("Invalid input, try again.");
        }

        //step 2. initialize the dictionary of legal words
        Dictionary boggleDict = new Dictionary("wordlist.txt"); //you may have to change the path to the wordlist, depending on where you place it.

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

        System.out.println("\nIt's Player 1's turn to find some words!\n");
        String inPut;

        while(true) {
            //step 1. Print the board for the user, so they can scan it for words
            System.out.println(this.grid.toString());
            System.out.print("Enter a word or press the Enter key to end turn: ");


            //step 2. Get a input (a word) from the user via the console
            inPut = scanner.nextLine().toUpperCase();

            if(inPut == "") {
                break; //end round if user inputs nothing
            }

            //step 3. Check to see if it is valid (note validity checks should be case-insensitive)
            if (this.allWords.containsKey((inPut.toUpperCase()))) {
                //step 4. If it's valid, update the player's word list and score (stored in boggleStats)
                gameStats.addWord(inPut.toUpperCase(), BoggleStats.Player.Player1);
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

        System.out.println("\nIt's Player 2's turn to find some words!\n");
        String inPut;

        while(true) {
            //step 1. Print the board for the user, so they can scan it for words
            System.out.println(this.grid.toString());
            System.out.print("Enter a word or press the Enter key to end turn: ");


            //step 2. Get a input (a word) from the user via the console
            inPut = scanner.nextLine().toUpperCase();

            if(inPut == "") {
                break; //end round if user inputs nothing
            }

            //step 3. Check to see if it is valid (note validity checks should be case-insensitive)
            if (this.allWords.containsKey((inPut.toUpperCase()))) {
                //step 4. If it's valid, update the player's word list and score (stored in boggleStats)
                gameStats.addWord(inPut.toUpperCase(), BoggleStats.Player.Player2);
            } else {
                playInvalid();
            }
        }

    }
}