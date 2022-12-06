package boggle;

import java.util.HashSet;
import java.util.Set;

/**
 * The BoggleStats class for the first Assignment in CSC207, Fall 2022
 * The BoggleStats will contain statsitics related to game play Boggle
 */
public class BoggleStats {

    /**
     * set of words the player finds in a given round
     */
    private Set<String> playerWords = new HashSet<String>();
    /**
     * set of words the computer finds in a given round
     */
    private Set<String> computerWords = new HashSet<String>();
    /**
     * the player's score for the current round
     */
    private int pScore;
    /**
     * the computer's score for the current round
     */
    private int cScore;
    /**
     * the player's total score across every round
     */
    private int pScoreTotal;
    /**
     * the computer's total score across every round
     */
    private int cScoreTotal;
    /**
     * the average number of words, per round, found by the player
     */
    private double pAverageWords;
    /**
     * the average number of words, per round, found by the computer
     */
    private double cAverageWords;
    /**
     * the current round being played
     */
    private int round;

    /**
     * enumarable types of players (human or computer)
     */
    public enum Player {
        Player1("Player 1"),
        Player2("Player 2");
        private final String player;
        Player(final String player) {
            this.player = player;
        }
    }

    /* BoggleStats constructor
     * ----------------------
     * Sets round, totals and averages to 0.
     * Initializes word lists (which are sets) for computer and human players.
     */
    public BoggleStats() {

        this.round = 0;
        this.cScoreTotal = 0;
        this.pScoreTotal = 0;
        this.cAverageWords = 0;
        this.pAverageWords = 0;
    }

    /*
     * Add a word to a given player's word list for the current round.
     * You will also want to increment the player's score, as words are added.
     *
     * @param word     The word to be added to the list
     * @param player  The player to whom the word was awarded
     */
    public void addWord(String word, Player player) {

        if (player == Player.Player1) {
            if (!this.playerWords.contains(word)) {     // Make sure no duplicated word
                this.playerWords.add(word);
                this.pScore += 1 + word.length() - 4;
            }
        }
        else {
            if (!this.playerWords.contains(word)) {
                this.computerWords.add(word);
                this.cScore += 1 + word.length() - 4;
            }
        }
    }

    /*
     * End a given round.
     * This will clear out the human and computer word lists, so we can begin again.
     * The function will also update each player's total scores, average scores, and
     * reset the current scores for each player to zero.
     * Finally, increment the current round number by 1.
     */
    public void endRound() {

        //The number of rounds played is the current round number + 1
        int numRounds = this.round + 1;

        // Update total scores for both players
        this.pScoreTotal += this.pScore;
        this.cScoreTotal += this.cScore;

        // Update the average number of words per round
        this.pAverageWords = (this.pAverageWords * (numRounds - 1) + this.playerWords.size()) / numRounds;
        this.cAverageWords = (this.cAverageWords * (numRounds - 1) + this.computerWords.size()) / numRounds;

        //reset current scores for each player to zero
        this.pScore = 0;
        this.cScore = 0;

        //clears out human and computer word lists
        this.playerWords.clear();
        this.computerWords.clear();

        //increment current round number by 1
        this.round += 1;
    }

    /*
     * Summarize one round of boggle.  Print out:
     * The words each player found this round.
     * Each number of words each player found this round.
     * Each player's score this round.
     */
    public void summarizeRound(String player1Name, String player2Name) {

        // We want round # starts from 1
        int roundNum = this.round + 1;
        System.out.println("Summary for round: " + roundNum);

        //displays words Player found this round
        System.out.println("Number of words found by " + player1Name + ":" + this.playerWords.size());

        System.out.println(this.playerWords);
        System.out.println("");

        //displays words Computer found this round
        System.out.println("Number of words found by " + player2Name + ":" + this.computerWords.size());

        System.out.println(this.computerWords);
        System.out.println("");

        //displays each player's score this round
        System.out.println(player1Name + " scored " + this.pScore + " points this round.");
        System.out.println(player2Name + " scored " + this.cScore + " points this round.");
    }

    /*
     * Summarize the entire boggle game.  Print out:
     * The total number of rounds played.
     * The total score for either player.
     * The average number of words found by each player per round.
     */
    public void summarizeGame(String player1Name, String player2Name) {

        System.out.println("You have played a total of " + this.round + " round(s).");

        System.out.println(player1Name + "'s total score: " + this.pScoreTotal);
        System.out.println(player2Name + "'s total score: " + this.cScoreTotal);

        System.out.println("Average number of words " + player1Name + " found per round: " + this.pAverageWords);
        System.out.println("Average number of words " + player2Name + " found per round: " + this.cAverageWords);

    }

    /*
     * @return Set<String> The player's word list
     */
    public Set<String> getPlayerWords() {
        return this.playerWords;
    }

    /*
     * @return int The number of rounds played
     */
    public int getRound() { return this.round; }

    /*
     * @return int The current player score
     */
    public int getScore() {
        return this.pScore;
    }

}
