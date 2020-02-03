package edu.sdonohue.advancedjava;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class for maintaining a score pad for a game that can accept the starting
 * scores of all players, and round by round modifications to each player's
 * score. Each round will be saved, so that the full round history and current
 * scores can be viewed at any time.
 */
public class ScorePad {
    //Scorekeeper to hold the current scores
    private ScoreKeeper currentScores;
    // History of each round's changes
    private List<ScoreKeeper> rounds;

    /**
     * Constructs a ScorePad using an injected ScoreKeeper to maintain each
     * Player's current score. The ScoreKeeper should already be
     * initialized with a list of Players and their starting scores.
     *
     * @param startingScores
     */
    public ScorePad(ScoreKeeper startingScores){
        this.currentScores = startingScores;
        rounds = new LinkedList<ScoreKeeper>();
    }

    /**
     * Records the changes in scores for a given round of the game and stores
     * the round in history for later display. Not every Player in the game need
     * be included in the round if their score should not be modified.
     *
     * @param round A ScoreKeeper that includes the changes to each Player's score for the round
     */
    public void scoreRound(ScoreKeeper round){
        //todo: Verify the round has the no invalid players
        if (round == null){
            throw new IllegalArgumentException("Round is null");
        }

        Set<Player> playerSet = round.getPlayerSet();
        for (Player player: playerSet){
            int amount = round.getPlayerScore(player);
            currentScores.addToPlayerScore(player, amount);
        }
        rounds.add(round);
    }

    /**
     * Get the current score for a Player.
     *
     * @param player The Player whose score should be returned
     * @return The current score of the player
     */
    public int getPlayerScore(Player player){
        return currentScores.getPlayerScore(player);
    }

    /**
     * --Not yet implemented--
     * Displays the scorepad, starting with the first round and
     * ending with the current scores.
     */
    @Deprecated
    public void displayScorePad(){
        //todo: display the scorepad
        throw new UnsupportedOperationException("Display of the scorepad is not supported");
    }

}
