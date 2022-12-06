package boggle;

import java.util.*;

public class SinglePlayer extends BoggleGame{


    /**
     * scanner used to interact with the user via console.
     */
    public Scanner scanner;

    /**
     * stores game statistics.
     */
    private BoggleStats gameStats;

    public SinglePlayer(BoggleStats gameStats) {
        this.scanner = new Scanner(System.in);
        this.gameStats = gameStats;
    }

    /*
     * Play a round of Boggle.
     * This initializes the main objects: the board, the dictionary, the map of all
     * words on the board, and the set of words found by the user. These objects are
     * passed by reference from here to many other functions.
     */
    public void playRound(int size, String letters){
        //step 1. initialize the grid
        BoggleGrid grid = new BoggleGrid(size);
        grid.initalizeBoard(letters);

        //step 2. initialize the dictionary of legal words
        Dictionary boggleDict = new Dictionary("wordlist.txt"); //you may have to change the path to the wordlist, depending on where you place it.

        //step 3. find all legal words on the board, given the dictionary and grid arrangement.
        Map<String, ArrayList<Position>> allWords = new HashMap<String, ArrayList<Position>>();
        findAllWords(boggleDict, grid);

        //step 4. allow the user to try to find some words on the grid
        humanMove();

        //step 5. allow the computer to identify remaining words
        computerMove();
    }

    /*
     * Gets words from the user.  As words are input, check to see that they are valid.
     * If yes, add the word to the player's word list (in boggleStats) and increment
     * the player's score (in boggleStats).
     * End the turn once the user hits return (with no word).
     *
     */
    private void humanMove(){
        System.out.println("\nIt's your turn to find some words!\n");
        String inPut;

        while(true) {
            //step 1. Print the board for the user, so they can scan it for words
            System.out.println(this.grid.toString());
            System.out.print("Enter a word or press the Enter key to end: ");

            //step 2. Get a input (a word) from the user via the console
            inPut = scanner.nextLine();
            if(inPut == "") break; //end round if user inputs nothing

            //step 3. Check to see if it is valid (note validity checks should be case-insensitive)
            if (this.allWords.containsKey((inPut.toUpperCase()))) {
                //step 4. If it's valid, update the player's word list and score (stored in boggleStats)
                gameStats.addWord(inPut.toUpperCase(), BoggleStats.Player.Player1);
            }
        }
    }

    /*
     * Gets words from the computer.  The computer should find words that are
     * both valid and not in the player's word list.  For each word that the computer
     * finds, update the computer's word list and increment the
     * computer's score (stored in boggleStats).
     *
     */
    private void computerMove(){
        Set<String> player_words = gameStats.getPlayerWords();

        for (String word : this.allWords.keySet()) {
            if (!player_words.contains(word))
                gameStats.addWord(word, BoggleStats.Player.Player2);
        }
    }
}
