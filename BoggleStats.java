package boggle;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.scene.media.*;

import java.io.File;

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
    private Set<String> player1Words = new HashSet<String>();
    /**
     * set of words the computer finds in a given round
     */
    private Set<String> player2Words = new HashSet<String>();
    /**
     * the player's score for the current round
     */
    private int p1Score;
    /**
     * the computer's score for the current round
     */
    private int p2Score;
    /**
     * the player's total score across every round
     */
    private int p1ScoreTotal;
    /**
     * the computer's total score across every round
     */
    private int p2ScoreTotal;
    /**
     * the average number of words, per round, found by the player
     */
    private double p1AverageWords;
    /**
     * the average number of words, per round, found by the computer
     */
    private double p2AverageWords;
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
        this.p2ScoreTotal = 0;
        this.p1ScoreTotal = 0;
        this.p2AverageWords = 0;
        this.p1AverageWords = 0;
    }

    /*
     * Add a word to a given player's word list for the current round.
     * You will also want to increment the player's score, as words are added.
     *
     * @param word     The word to be added to the list
     * @param player  The player to whom the word was awarded
     */
    public void addWord(String word, Player player, String mode) {
        if (player == Player.Player1) {
            playValid();
            if (!this.player1Words.contains(word)) {     // Make sure no duplicated word
                playValid();
                this.player1Words.add(word);
                this.p1Score += 1 + word.length() - 4;
            }
            else {playInvalid();}
        }
        else {
            if (!this.player1Words.contains(word)) {
                if(mode.contains("m"){playValid();}
                this.player2Words.add(word);
                this.p2Score += 1 + word.length() - 4;
            } else if(mode.contains("m")){playInvalid();}
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
        this.p1ScoreTotal += this.p1Score;
        this.p2ScoreTotal += this.p2Score;

        // Update the average number of words per round
        this.p1AverageWords = (this.p1AverageWords * (numRounds - 1) + this.player1Words.size()) / numRounds;
        this.p2AverageWords = (this.p2AverageWords * (numRounds - 1) + this.player2Words.size()) / numRounds;

        //reset current scores for each player to zero
        this.p1Score = 0;
        this.p2Score = 0;

        //clears out human and computer word lists
        this.player1Words.clear();
        this.player2Words.clear();

        //increment current round number by 1
        this.round += 1;
    }

    /*
     * Summarize one round of boggle.  Print out:
     * The words each player found this round.
     * Each number of words each player found this round.
     * Each player's score this round.
     *
     * @param player1Name The name of Player 1
     * @param player2Name The name of Player 2
     */
    public void summarizeRound(String player1Name, String player2Name) {

        // We want round # starts from 1
        int roundNum = this.round + 1;
        System.out.println("Summary for round: " + roundNum);

        //displays words Player found this round
        System.out.println("Number of words found by " + player1Name + ":" + this.player1Words.size());

        System.out.println(this.player1Words);
        System.out.println("");

        //displays words Computer found this round
        System.out.println("Number of words found by " + player2Name + ":" + this.player2Words.size());

        System.out.println(this.player2Words);
        System.out.println("");

        //displays each player's score this round
        System.out.println(player1Name + " scored " + this.p1Score + " points this round.");
        System.out.println(player2Name + " scored " + this.p2Score + " points this round.");
    }

    /*
     * Summarize the entire boggle game.  Print out:
     * The total number of rounds played.
     * The total score for either player.
     * The average number of words found by each player per round.
     *
     * @param player1Name
     * @param player2Name
     */
    public void summarizeGame(String player1Name, String player2Name) {

        System.out.println("You have played a total of " + this.round + " round(s).");

        System.out.println(player1Name + "'s total score: " + this.p1ScoreTotal);
        System.out.println(player2Name + "'s total score: " + this.p2ScoreTotal);

        System.out.println("Average number of words " + player1Name + " found per round: " + this.p1AverageWords);
        System.out.println("Average number of words " + player2Name + " found per round: " + this.p2AverageWords);

    }

    /*
     * @return Set<String> Player 1's word list
     */
    public Set<String> getPlayer1Words() {
        return this.player1Words;
    }

    /*
     * @return Set<String> Player 2's word list
     */
    public Set<String> getPlayer2Words() { return this.player2Words; }

    /*
     * @return int The number of rounds played
     */
    public int getRound() { return this.round; }

    /*
     * @return int The current Player 1 score
     */
    public int getPlayer1Score() {

        return this.p1Score;
    }

    /*
     * @return int The current Player 2 score
     */
    public int getPlayer2Score() {

        return this.p2Score;
    }

    public void playValid(){
        File file = new File("CorrectSound(1).mp3");
        String uri = file.toURI().toString();
        AudioClip audioClip = new AudioClip(uri);
        audioClip.play();
    }

    public void playInvalid(){
        File file = new File("IncorrectSound(1).mp3");
        String uri = file.toURI().toString();
        AudioClip audioClip = new AudioClip(uri);
        audioClip.play();
    }

}
