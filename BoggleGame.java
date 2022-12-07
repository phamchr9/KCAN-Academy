package boggle;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.scene.media.*;
import javafx.scene.text.Text;


import java.awt.*;
import java.io.File;
=======


import java.util.*;
import java.util.List;

/**
 * The BoggleGame class for the first Assignment in CSC207, Fall 2022
 */
public class BoggleGame {

    /**
     * stores a string representation of the board letters.
     */
    private String boardLetters;

    /**
     * stores the SinglePlayer object.
     */
    private SinglePlayer singlePlayer;

    /**
     * stores the MultiPlayer object.
     */
    private MultiPlayer multiPlayer;

    /**
     * stores the name of Player 1.
     */
    public String player1Name;

    /**
     * stores the name of Player 2.
     */
    public String player2Name;

    /**
     * stores string representation of the current game mode (1 for Single player; 2 for Multiplayer).
     */
    public String choiceGamemode;

    /**
     * stores the Editor object.
     */
    public Editor editor;

    /**
     * stores a mutable list of all the possible words that can be formed from the current board arrangement.
     */
    public Map<String, ArrayList<Position>> allWords;

    /**
     * stores the BoggleGrid object.
     */
    public BoggleGrid grid;

    /**
     * scanner used to interact with the user via console
     */
    public Scanner scanner;
    /**
     * stores game statistics
     */
    private BoggleStats gameStats;

    public Editor editor;

    public Map<String, ArrayList<Position>> allWords;

    public BoggleGrid grid;


    /**
     * dice used to randomize letter assignments for a small grid
     */
    private final String[] dice_small_grid= //dice specifications, for small and large grids
            {"AAEEGN", "ABBJOO", "ACHOPS", "AFFKPS", "AOOTTW", "CIMOTU", "DEILRX", "DELRVY",
                    "DISTTY", "EEGHNW", "EEINSU", "EHRTVW", "EIOSST", "ELRTTY", "HIMNQU", "HLNNRZ"};
    /**
     * dice used to randomize letter assignments for a big grid
     */
    private final String[] dice_big_grid =
            {"AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY",
                    "BJKQXZ", "CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DDHNOT", "DHHLOR",
                    "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"};

    /*
     * BoggleGame constructor
     */
    public BoggleGame() {
        this.scanner = new Scanner(System.in);
        this.gameStats = new BoggleStats();
    }

    /*
     * Provide instructions to the user, so they know how to play the game.
     */
    public void giveInstructions()
    {
        System.out.println("The Boggle board contains a grid of letters that are randomly placed.");
        System.out.println("We're both going to try to find words in this grid by joining the letters.");
        System.out.println("You can form a word by connecting adjoining letters on the grid.");
        System.out.println("Two letters adjoin if they are next to each other horizontally, ");
        System.out.println("vertically, or diagonally. The words you find must be at least 4 letters long, ");
        System.out.println("and you can't use a letter twice in any single word. Your points ");
        System.out.println("will be based on word length: a 4-letter word is worth 1 point, 5-letter");
        System.out.println("words earn 2 points, and so on. After you find as many words as you can,");
        System.out.println("I will find all the remaining words.");
        System.out.println("\nHit return when you're ready...");
    }

    /*
     * Allows the user to choose a game mode.
     */
    public void setGamemode() {
        //prompts user to choose a game mode
        System.out.println("Enter 1 to play Single player; 2 to play Multiplayer:");
        choiceGamemode = scanner.nextLine();

        //checks if the input is valid
        while(!choiceGamemode.equals("1") && !choiceGamemode.equals("2")){
            System.out.println("Please try again.");
            System.out.println("Enter 1 to play Single player; 2 to play Multiplayer:");
            choiceGamemode = scanner.nextLine();
        }
    }


    /*
     * Gets information from the user to initialize a new Boggle game.
     * It will loop until the user indicates they are done playing.
     */
    public void playGame(){
        int boardSize;

        while(true){
            //prompts user to choose a grid size
            System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
            String choiceGrid = scanner.nextLine();

            //get grid size preference
            if(choiceGrid == "") break; //end game if user inputs nothing
            while(!choiceGrid.equals("1") && !choiceGrid.equals("2")){
                System.out.println("Please try again.");
                System.out.println("Enter 1 to play on a big (5x5) grid; 2 to play on a small (4x4) one:");
                choiceGrid = scanner.nextLine();
            }

            //sets the size of the grid
            if(choiceGrid.equals("1")) boardSize = 5;
            else boardSize = 4;

            //get letter choice preference
            System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
            String choiceLetters = scanner.nextLine();

            //checks if the input is valid
            if(choiceLetters == "") break; //end game if user inputs nothing
            while(!choiceLetters.equals("1") && !choiceLetters.equals("2")){
                System.out.println("Please try again.");
                System.out.println("Enter 1 to randomly assign letters to the grid; 2 to provide your own.");
                choiceLetters = scanner.nextLine();
            }

            //sets the letters for the grid
            if(choiceLetters.equals("1")){
                boardLetters = randomizeLetters(boardSize);
            } else {
                //prompts the user for a string representation of their choice of board letters
                System.out.println("Input a list of " + boardSize*boardSize + " letters:");
                choiceLetters = scanner.nextLine();

                //checks if the input is valid
                while(!(choiceLetters.length() == boardSize*boardSize)){
                    System.out.println("Sorry, bad input. Please try again.");
                    System.out.println("Input a list of " + boardSize*boardSize + " letters:");
                    choiceLetters = scanner.nextLine();
                }
                boardLetters = choiceLetters.toUpperCase();
            }

            //starts the round
            if (choiceGamemode.equals("1")) {
                this.player1Name = "Human";
                this.player2Name = "Computer";
                this.singlePlayer = new SinglePlayer(this.gameStats);
                singlePlayer.playRound(boardSize, boardLetters);
            } else if (choiceGamemode.equals("2")) {
                this.player1Name = "Player 1";
                this.player2Name = "Player 2";
                this.multiPlayer = new MultiPlayer(this.gameStats);
                multiPlayer.playRound(boardSize, boardLetters);
            }

            //round is over! So, store the statistics, and end the round.
            this.gameStats.summarizeRound(player1Name, player2Name);
            this.gameStats.endRound();

            //Shall we repeat?
            System.out.println("Play again? Type 'Y' or 'N'");
            String choiceRepeat = scanner.nextLine().toUpperCase();

            if(choiceRepeat == "") break; //end game if user inputs nothing

            //checks if the input is valid
            while(!choiceRepeat.equals("Y") && !choiceRepeat.equals("N")){
                System.out.println("Please try again.");
                System.out.println("Play again? Type 'Y' or 'N'");
                choiceRepeat = scanner.nextLine().toUpperCase();
            }

            if(choiceRepeat == "" || choiceRepeat.equals("N")) break; //end game if user inputs nothing

        }

        //we are done with the game! So, summarize all the play that has transpired and exit.
        this.gameStats.summarizeGame(player1Name, player2Name);
        System.out.println("Thanks for playing!");
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

    }

    public void updateEditor(BoggleGrid boggleGrid){
        this.editor.bogglegrid = boggleGrid;
        this.editor.addMemento();
    }
    /*
     * This method returns a String of letters (length 16 or 25 depending on the size of the grid).
     * There is one letter per grid position. The letters fill the grid from left to right and
     * from top to bottom.
     *
     * @param size An integer representation of the board dimensions (4 for 4-by-4 grid or 5 for 5-by-5 grid)
     *
     * @return String a String of random letters (length 16 or 25 depending on the size of the grid)
     */
    public String randomizeLetters(int size){

        Random rand = new Random();
        String letters = "";
        String[] dice_grid;
        int len = size * size;
        int newPos;

        String tempDice;

        if (size == 4)  //small 4x4 grid
            dice_grid = this.dice_small_grid;
        else dice_grid = this.dice_big_grid;

        // Radomly shuffles the positions of the dice
        for (int i = 0; i < len; i++) {
            // Pick a postion to swap
            newPos = rand.nextInt(len);

            // Swap the positions if they are different
            if (i != newPos) {
                tempDice = dice_grid[i];
                dice_grid[i] = dice_grid[newPos];
                dice_grid[newPos] = tempDice;
            }
        }

        // Randomly selects one of the letters on the given die at each grid position to determine
        // the letter at the given position

        for (int i = 0; i < len; i++) {
            letters = letters + dice_grid[i].charAt(rand.nextInt(dice_grid[i].length()));
        }

        return letters;
    }

    public void shuffle(){
        updateEditor(this.grid);
        Dictionary boggleDict = new Dictionary("wordlist.txt");

        BoggleGrid new_grid = new BoggleGrid(this.grid.size);
        new_grid.initalizeBoard(randomizeLetters(this.grid.size));

        this.grid = new_grid;
        this.grid.takeSnapshot();
    }

    public void undo(){

    }

    public void playInvalid(){

    }

    /*
     * This function finds all valid words on the boggle board.
     * A valid word is from the dictionary, has at least 4 characters and can be found on the board.
     *
     * For each word (having at least 4 characters) from the dictionary, this function calls the recursive,
     * helper function "checkWord" to see if the word can be found on the board. It then adds to the allWords
     * Map accordingly.
     *
     * @param allWords A mutable list of all legal words that can be found, given the boggleGrid grid letters
     * @param boggleDict A dictionary of legal words
     * @param boggleGrid A boggle grid, with a letter at each position on the grid
     */
     
    public void findAllWords(Dictionary boggleDict, BoggleGrid boggleGrid) {

        List<String> words = boggleDict.validWords();

        int numRows = boggleGrid.numRows();
        int numCols = boggleGrid.numCols();
        boolean foundWord = false;

        for (String w : words) {
            ArrayList<Position> path = new ArrayList<Position>();
            foundWord = false;
            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numCols; c++) {
                    if (checkWord(w, path, r, c, boggleGrid)) {
                        this.allWords.put(w, path);
                        foundWord = true;
                        break;
                    }

                }
                if (foundWord) break;
            }

        }
    }

    /*
     * This function to check whether position p is in the list pList
     *
     * @param pList list of positions.
     * @param p position to check
     */
    private boolean inList (ArrayList<Position> pList, Position p) {
        for (Position pos : pList) {
            if (p.getRow() == pos.getRow() & p.getCol() == pos.getCol())
                return true;
        }
        return false;
    }

    /*
     * This helper function checks to see if a word can be found on the current board starting a specific location.
     *
     * If the first character of the string matches the character at this board location, the function is called
     * again to check to see the remainder of the string can be found from the 8 adjacent neighbours
     *
     * @param word the word to check
     * @param path list of positions visited corresponding to the characters found so far.
     * @param row position's row
     * @param col position's col
     * @param grid a BoggleGrid
     *
     */
    private boolean checkWord(String word, ArrayList<Position> path, int row, int col, BoggleGrid grid) {
        int maxRow = grid.numRows();
        int maxCol = grid.numCols();
        Position p = new Position(row, col);


        // Check if the position is out of range.
        if (row < 0 || row >= maxRow || col < 0 || col >= maxCol) return false;

        // Check if this position is already in the list of positions of this word
        if (inList(path, p))  return false;

        // Check the first character of the string matches the current character of the board
        char curChar = grid.getCharAt(row, col);
        if (curChar != word.charAt(0)) return false;

        // The characters match. Add this position to the list
        path.add(p);

        // If this is the last character of the string then we're done
        if (word.length() == 1) return true;

        // Recursively call the same function for all 8 neighbors of this position
        String newWord = word.substring(1);

        // Right column
        if (checkWord(newWord, path, row - 1, col + 1, grid)) return true;
        if (checkWord(newWord, path, row,col + 1, grid)) return true;
        if (checkWord(newWord, path, row + 1, col + 1, grid)) return true;

        // Middle column
        if (checkWord(newWord, path, row + 1, col, grid)) return true;
        if (checkWord(newWord, path, row - 1, col, grid)) return true;

        // Left column
        if (checkWord(newWord, path, row - 1, col - 1, grid)) return true;
        if (checkWord(newWord, path, row,col - 1, grid)) return true;
        if (checkWord(newWord, path, row + 1, col - 1, grid)) return true;

        // Fail. Remove this position from the list
        path.remove(p);

        return false;
    }

    // Shuffles bogglegrid by initilizing a new grid of the same size with new letters
    
    public void shuffle() {
        updateEditor(this.grid);
        Dictionary boggleDict = new Dictionary("wordlist.txt");

        BoggleGrid new_grid = new BoggleGrid(this.grid.size);
        new_grid.initalizeBoard(randomizeLetters(this.grid.size));

        this.grid = new_grid;
        this.grid.takeSnapshot();
    }

    public void undo() {

    }

    public void playValid() {


    }

    public void playInvalid() {

    }

    public void updateEditor(BoggleGrid boggleGrid) {

    }




}
